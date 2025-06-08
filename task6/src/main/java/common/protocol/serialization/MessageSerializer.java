package common.protocol.serialization;

import common.protocol.messages.Message;

public interface MessageSerializer<T> {
    String serialize(Message message) throws Exception;
}
