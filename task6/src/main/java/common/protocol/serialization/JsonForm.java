package common.protocol.serialization;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import common.protocol.messages.*;

public class JsonForm implements MessageSerializer<String>, MessageDeserializer<String> {
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        mapper.registerSubtypes(
                new com.fasterxml.jackson.databind.jsontype.NamedType(LoginRequest.class, MessageType.LOGIN_REQ.name()),
                new com.fasterxml.jackson.databind.jsontype.NamedType(LoginResponseMessage.class, MessageType.LOGIN_RESP.name()),
                new com.fasterxml.jackson.databind.jsontype.NamedType(TextMessage.class, MessageType.TEXT.name()),
                new com.fasterxml.jackson.databind.jsontype.NamedType(UsersListMessage.class, MessageType.USERS_LIST.name()),
                new com.fasterxml.jackson.databind.jsontype.NamedType(SystemMessage.class, MessageType.SYSTEM.name())
        );
    }

    @Override
    public String serialize(Message message) throws Exception {
        return mapper.writeValueAsString(message);
    }

    @Override
    public Message deserialize(String message) throws Exception {
        return mapper.readValue(message, Message.class);
    }
}
