package common.protocol.messages;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = LoginRequest.class, name = "LOGIN_REQ"),
        @JsonSubTypes.Type(value = LoginResponseMessage.class, name = "LOGIN_RESPONSE"),
        @JsonSubTypes.Type(value = TextMessage.class, name = "TEXT"),
        @JsonSubTypes.Type(value = SystemMessage.class, name = "SYSTEM"),
        @JsonSubTypes.Type(value = UsersListMessage.class, name = "USERS_LIST"),
        @JsonSubTypes.Type(value = DisconnectMessage.class, name = "DISCONNECT")
})
public interface Message {
    MessageType getType();
}
