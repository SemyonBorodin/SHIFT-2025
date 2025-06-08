package client.model;

import common.protocol.messages.Message;

public interface ChatObserver {
    void onMessageReceived(Message message) throws Exception;

    void onConnectionLost();
}
