package common.protocol.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TextMessage implements ServerMessage {
    @JsonProperty
    private final String sender;
    @JsonProperty
    private final String text;
    @JsonProperty
    private final long timestamp;

    @JsonCreator
    public TextMessage(
            @JsonProperty String sender,
            @JsonProperty String text,
            @JsonProperty long timestamp
    ) {
        this.sender = sender;
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    @Override
    public MessageType getType() {
        return MessageType.TEXT;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "sender='" + sender + '\'' +
                ", text='" + text + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
