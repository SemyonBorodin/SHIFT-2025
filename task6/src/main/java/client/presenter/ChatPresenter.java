package client.presenter;

import client.model.ChatObserver;
import client.model.ClientModel;
import client.view.ChatView;
import common.protocol.messages.*;

import javax.swing.*;
import java.util.Arrays;

public class ChatPresenter implements ChatObserver {
    private final ClientModel model;
    private final ChatView view;

    public ChatPresenter(ClientModel model, ChatView view) {
        this.model = model;
        this.view = view;
        model.addObserver(this);
    }

    public void start() {
        SwingUtilities.invokeLater(() -> {
            view.setConnectAction(this::connect);
            view.setSendAction(this::sendMessage);
            view.setDisconnectAction(model::disconnect);
            view.showLoginDialog();
        });
    }

    private void connect() {
        try {
            model.connect(view.getServerAddress(), view.getServerPort(), view.getUsername());
            view.showChatWindow();
        } catch (Exception e) {
            view.showError("Ошибка подключения: " + e.getMessage());
        }
    }

    private void sendMessage() {
        try {
            model.sendText(view.getMessageText());
        } catch (Exception e) {
            view.showError("Ошибка отправки: " + e.getMessage());
        }
    }

    @Override
    public void onMessageReceived(Message message) {
        System.out.println("Получено сообщение типа: " + message.getType());
        if (message instanceof TextMessage) {
            view.appendMessage(message);
        } else if (message instanceof SystemMessage) {
            view.appendMessage(message);
        } else if (message instanceof UsersListMessage) {
            view.updateParticipants(((UsersListMessage) message).getUsers());
            System.out.println("Список участников обновлён: "
                    + message);
        } else if (message instanceof LoginResponseMessage) {
            var response = (LoginResponseMessage) message;
            if (!response.isSuccess()) {
                view.appendMessage(new SystemMessage("Ошибка: "
                        + response.getError(), System.currentTimeMillis()));
            } else {
                System.out.println("Успешный логин для user: " + model.getUsername());
            }
        } else if (message instanceof DisconnectMessage) {
            view.appendMessage(message);
        }
    }

    @Override
    public void onConnectionLost() {
        view.appendMessage(new SystemMessage("Соединение утеряно", System.currentTimeMillis()));
        view.showLoginDialog();
    }
}