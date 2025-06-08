package server;

import common.protocol.messages.Message;
import common.protocol.messages.SystemMessage;
import common.protocol.messages.UsersListMessage;
import server.Exceptions.NameConflictException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private final int port;
    private final List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());
    private volatile ServerSocket serverSocket;
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public ChatServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Сервер запущен на порту " + port);

        executor.submit(() -> {
            while (!serverSocket.isClosed()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Новый клиент подключён: " + clientSocket.getInetAddress());
                    ClientHandler handler = new ClientHandler(clientSocket, this);
                    handler.start();
                    clients.add(handler);
                    handler.start();
                } catch (IOException e) {
                    if (!serverSocket.isClosed()) {
                        System.err.println("Ошибка при подключении клиента: " + e.getMessage());
                    }
                }
            }
        });
    }

    public void stop() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при закрытии сокета сервера: " + e.getMessage());
        }
        synchronized (clients) {
            List<ClientHandler> clientsToDisconnect = new ArrayList<>(clients);
            for (ClientHandler client : clientsToDisconnect) {
                client.disconnect();
            }
            clients.clear();
            System.out.println("Список клиентов очищен по завершении");
        }
        executor.shutdownNow();
        try {
            if (!executor.awaitTermination(15, java.util.concurrent.TimeUnit.SECONDS)) {
                System.err.println("ExecutorService не завершил задачи в течение 15 секунд");
            }
        } catch (InterruptedException e) {
            System.err.println("Ожидание завершения задач прервано: " + e.getMessage());
        }
    }

    public void addClient(ClientHandler client) throws NameConflictException {
        synchronized (clients) {
            String clientName = client.getClientName();
            if (clientName != null && clients.stream()
                    .anyMatch(c -> c != client
                            && c.getClientName() != null
                            && c.getClientName().equals(clientName))) {
                throw new NameConflictException("Имя пользователя занято, выберете другое имя");
            }
        }
    }

    public void removeClient(ClientHandler client) {
        synchronized (clients) {
            if (clients.remove(client)) {
                String clientName = client.getClientName();
                if (clientName != null) {
                    broadcast(new SystemMessage(clientName + " покинул чат", System.currentTimeMillis()));
                    sendParticipants();
                }
            }
        }
    }

    public void broadcast(Message message) {
        synchronized (clients) {
            for (ClientHandler client : new ArrayList<>(clients)) {
                try {
                    client.sendMessage(message);
                } catch (Exception e) {
                    System.out.println("Не удалось отправить " + client.getClientName() + ": " + e.getMessage());
                }
            }
        }
    }

    public void sendParticipants() {
        String[] participants;
        synchronized (clients) {
            participants = clients.stream()
                    .map(ClientHandler::getClientName)
                    .filter(Objects::nonNull)
                    .toArray(String[]::new);
        }
        broadcast(new UsersListMessage(participants, System.currentTimeMillis()));
    }
}