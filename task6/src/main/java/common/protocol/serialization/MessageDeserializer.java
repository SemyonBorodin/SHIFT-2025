package common.protocol.serialization;

import common.protocol.messages.Message;

public interface MessageDeserializer<T> {
    Message deserialize(String message) throws Exception;
}
