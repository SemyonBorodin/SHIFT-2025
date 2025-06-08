package common.protocol.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponseMessage implements ServerMessage {
    @JsonProperty
    private final boolean success;
    @JsonProperty
    private final String error;
    @JsonProperty
    private final long timestamp;

    @JsonCreator
    public LoginResponseMessage(
            @JsonProperty boolean success,
            @JsonProperty String error,
            @JsonProperty long timestamp
    ) {
        this.success = success;
        this.error = error;
        this.timestamp = timestamp;
    }

    @Override
    public MessageType getType() {
        return MessageType.LOGIN_RESP;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

    public String toString() {
        return "LoginResponseMessage{" +
                "success=" + success +
                ", errorMessage='" + error + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

