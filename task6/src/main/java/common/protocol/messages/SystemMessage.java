package common.protocol.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SystemMessage implements ServerMessage {
    @JsonProperty
    private final String text;
    @JsonProperty
    private final long timestamp;

    @JsonCreator
    public SystemMessage(
            @JsonProperty String text,
            @JsonProperty long timestamp
    ) {
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    @Override
    public MessageType getType() {
        return MessageType.SYSTEM;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "SystemMessage{" +
                ", text='" + text + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
