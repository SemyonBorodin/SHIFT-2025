package server;

import common.protocol.chanel.Channel;
import common.protocol.chanel.ChatChannel;
import common.protocol.messages.*;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private final Channel channel;
    private volatile String username;
    private final ChatServer server;

    public ClientHandler(Socket socket, ChatServer server) throws IOException {
        this.channel = new ChatChannel(socket);
        this.server = server;
    }

    public void start() {
        new Thread(() -> {
            try {
                channel.readLoop(this::handleMessage);
            } catch (Exception e) {
                disconnect();
            }
        }).start();
    }

    private void handleMessage(Message message) {
        switch (message.getType()) {
            case LOGIN_REQ -> handleLogin((LoginRequest) message);
            case TEXT -> handleText((TextMessage) message);
            case DISCONNECT -> handleDisconnect((DisconnectMessage) message);
            default -> System.err.println("Неизвестный тип сообщения: " + message.getType());
        }
    }

    private void handleLogin(LoginRequest message) {
        username = message.getUsername();
        channel.setUsername(username);
        try {
            server.addClient(this);
            channel.send(new LoginResponseMessage(true, null, System.currentTimeMillis()));
            server.broadcast(new SystemMessage(username + " присоединился", System.currentTimeMillis()));
            server.sendParticipants();
            System.out.println("Клиент " + username + " успешно зарегистрирован");
        } catch (Exception e) {
            try {
                channel.send(new LoginResponseMessage(false, "Ошибка регистрации: " + e.getMessage(), System.currentTimeMillis()));
                System.err.println("Ошибка регистрации для клиента: " + username + ": " + e.getMessage());
            } catch (Exception e1) {
                System.err.println("Не удалось отправить отчет об ошибке LoginResponseMessage: " + e1.getMessage());
            }
            disconnect();
        }
    }

    private void handleText(TextMessage message) {
        try {
            server.broadcast(message);
            System.out.println("Сообщение от " + message.getSender() + " отправлено: " + message.getText());
        } catch (Exception e) {
            System.err.println("Ошибка рассылки сообщения: " + e.getMessage());
        }
    }

    private void handleDisconnect(DisconnectMessage message) {
        disconnect();
        System.out.println("Клиент " + username + " отключился");
    }

    public void sendMessage(Message message) throws Exception {
        channel.send(message);
    }

    public String getClientName() {
        return username;
    }

    public void disconnect() {
        try {
            server.broadcast(new DisconnectMessage(username, System.currentTimeMillis()));
        } catch (Exception e) {
            System.err.println("Ошибка отправки DisconnectMessage: " + e.getMessage());
        }
        server.removeClient(this);
        try {
            channel.close();
        } catch (Exception e) {
            System.err.println("Ошибка закрытия channel: " + e.getMessage());
        }
    }
}
