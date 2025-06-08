package common.protocol.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class UsersListMessage implements ServerMessage {
    @JsonProperty
    private final String[] users;
    @JsonProperty
    private final long timestamp;

    @JsonCreator
    public UsersListMessage(
            @JsonProperty String[] users,
            @JsonProperty long timestamp
    ) {
        this.users = users;
        this.timestamp = timestamp;
    }

    public String[] getUsers() {
        return users;
    }

    @Override
    public MessageType getType() {
        return MessageType.USERS_LIST;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "UsersListMessage{" +
                "users=" + Arrays.toString(users) +
                ", timestamp=" + timestamp +
                '}';
    }
}

