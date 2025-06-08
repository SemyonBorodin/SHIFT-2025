package common.protocol.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest implements Message {
    @JsonProperty
    private String username;


    @JsonCreator
    public LoginRequest(@JsonProperty String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public MessageType getType() {
        return MessageType.LOGIN_REQ;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "username='" + username + '\'' +
                '}';
    }
}
