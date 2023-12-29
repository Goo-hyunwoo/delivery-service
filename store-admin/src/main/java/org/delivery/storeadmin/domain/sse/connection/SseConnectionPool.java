package org.delivery.storeadmin.domain.sse.connection;

import org.delivery.storeadmin.domain.sse.connection.ifs.ConnectionPoolIfs;
import org.delivery.storeadmin.domain.sse.connection.model.UserSseConnection;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SseConnectionPool implements ConnectionPoolIfs<String, UserSseConnection> {

    // thread safe한 자료구조
    private final Map<String, UserSseConnection> connectionPool = new ConcurrentHashMap<>();

    @Override
    public void addSession(String uniqueKey, UserSseConnection session) {
        connectionPool.put(uniqueKey, session);
    }

    @Override
    public UserSseConnection getSession(String uniqueKey) {
        return connectionPool.get(uniqueKey);
    }

    @Override
    public void onCompletionCallback(UserSseConnection session) {
        connectionPool.remove(session.getUniqueKey());
    }
}
