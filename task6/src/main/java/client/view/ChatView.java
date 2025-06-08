package client.view;

import common.protocol.messages.Message;

public interface ChatView {
    void showLoginDialog();

    void showChatWindow();

    void appendMessage(Message message);

    void updateParticipants(String[] participants);

    void showError(String message);

    void setConnectAction(Runnable action);

    void setSendAction(Runnable action);

    void setDisconnectAction(Runnable action);

    String getUsername();

    String getServerAddress();

    int getServerPort();

    String getMessageText();
}
