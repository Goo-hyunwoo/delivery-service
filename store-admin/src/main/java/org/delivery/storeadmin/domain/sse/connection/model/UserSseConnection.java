package org.delivery.storeadmin.domain.sse.connection.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.delivery.storeadmin.domain.sse.connection.ifs.ConnectionPoolIfs;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;


@Getter
@ToString
@EqualsAndHashCode
public class UserSseConnection {

    private final String uniqueKey;
    private final SseEmitter sseEmitter;
    private ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs;
    private final ObjectMapper objectMapper;

    public static UserSseConnection connect(String uniqueKey,  ConnectionPoolIfs<String,UserSseConnection> connectionPoolIfs, ObjectMapper objectMapper) {
        return new UserSseConnection(uniqueKey, connectionPoolIfs, objectMapper);
    }

    private UserSseConnection(String uniqueKey,  ConnectionPoolIfs<String,UserSseConnection> connectionPoolIfs, ObjectMapper objectMapper) {
        this.uniqueKey = uniqueKey;
        this.sseEmitter = new SseEmitter(1000L * 60);
        this.connectionPoolIfs = connectionPoolIfs;
        this.objectMapper = objectMapper;

        this.sseEmitter.onCompletion(() -> {
            this.connectionPoolIfs.onCompletionCallback(this);
        });
        this.sseEmitter.onTimeout(() -> {
            this.sseEmitter.complete();
        });

        this.connectionPoolIfs.addSession(uniqueKey, this);

        sendMessage("onopen", "connect");
    }

    public void sendMessage(String eventName, Object data) {
        try {
            var json = this.objectMapper.writeValueAsString(data);
            var event = SseEmitter.event()
                    .name(eventName)
                    .data(json)
                    ;
            this.sseEmitter.send(event);
        } catch (IOException e) {
            this.sseEmitter.completeWithError(e);
        }
    }

    public void sendMessage(Object data) {
        try {
            var json = this.objectMapper.writeValueAsString(data);
            var event = SseEmitter.event()
                    .data(json)
                    ;
            this.sseEmitter.send(event);
        } catch (IOException e) {
            this.sseEmitter.completeWithError(e);
        }
    }

}
