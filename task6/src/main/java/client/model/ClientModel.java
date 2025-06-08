package client.model;

import common.protocol.chanel.Channel;
import common.protocol.chanel.ChatChannel;
import common.protocol.messages.LoginRequest;
import common.protocol.messages.LoginResponseMessage;
import common.protocol.messages.Message;
import common.protocol.messages.TextMessage;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientModel {
    private Channel channel;
    private String username;
    private List<ChatObserver> observers = new ArrayList<>();
    private Thread readThread;

    public void connect(String host, int port, String username) throws Exception {
        this.username = username;
        this.channel = new ChatChannel(new Socket(host, port));
        send(new LoginRequest(username));
        startReadLoop();
    }

    private void startReadLoop() {
        readThread = new Thread(() -> {
            try {
                channel.readLoop(this::handleMessage);
            } catch (Exception e) {
                notifyConnectionLost();
            }
        });
        readThread.start();
    }

    private void handleMessage(Message message) {
        observers.forEach(obs -> {
            try {
                obs.onMessageReceived(message);
                System.out.println("Сообщение получено: " + message.getType());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        if (message instanceof LoginResponseMessage) {
            var response = (LoginResponseMessage) message;
            if (!response.isSuccess()) {
                notifyConnectionLost();
                System.err.println("Ошибка лога: " + response.getError());
            }
        }
    }

    public void send(Message message) throws Exception {
        channel.send(message);
    }

    public void sendText(String text) throws Exception {
        send(new TextMessage(username, text, System.currentTimeMillis()));
    }

    public void disconnect() {
        try {
            if (readThread != null) readThread.interrupt();
            if (channel != null) channel.close();
        } catch (Exception e) {
            System.err.println("Ошибка закрытия: " + e.getMessage());
        }
        notifyConnectionLost();
    }

    public void addObserver(ChatObserver observer) {
        observers.add(observer);
    }

    private void notifyConnectionLost() {
        observers.forEach(ChatObserver::onConnectionLost);
    }

    public String getUsername() {
        return username;
    }
}
