package common.protocol.messages;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public interface ServerMessage extends Message, Serializable {
    @JsonIgnore
    MessageType getType();
    long getTimestamp();
}