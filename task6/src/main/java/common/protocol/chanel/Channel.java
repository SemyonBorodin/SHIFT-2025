package common.protocol.chanel;

import common.protocol.messages.Message;

import java.util.function.Consumer;

public interface Channel {
    void close() throws Exception;
    void send(Message message) throws Exception;
    void readLoop(Consumer<Message> messageConsumer) throws Exception;
    void setUsername(String username);
}
