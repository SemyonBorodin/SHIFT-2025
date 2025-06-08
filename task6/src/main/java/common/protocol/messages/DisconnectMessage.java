package common.protocol.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DisconnectMessage implements ServerMessage {
    @JsonProperty
    private final String username;
    @JsonProperty
    private final long timestamp;

    @JsonCreator
    public DisconnectMessage(
            @JsonProperty String username,
            @JsonProperty long timestamp
    ) {
        this.username = username;
        this.timestamp = timestamp;
    }

    @Override
    public MessageType getType() {
        return MessageType.DISCONNECT;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "DisconnectMessage{" +
                "username='" + username + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

