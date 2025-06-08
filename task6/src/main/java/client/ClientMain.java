package client;

import client.model.ClientModel;
import client.presenter.ChatPresenter;
import client.view.SwingChatView;

public class ClientMain {

    public static void main(String[] args) {
        ClientModel model = new ClientModel();
        SwingChatView view = new SwingChatView();
        new ChatPresenter(model, view).start();
    }
}
