package com.ssafy.api.service;

import io.openvidu.java.client.Connection;
import io.openvidu.java.client.Session;

import java.util.List;

public interface OpenviduService {
    Session createSession();
    List<Session> fetchActiveSessions();
    boolean closeSession(Session session);
    String createConnection(Session session);
    List<Connection> fetchActiveConnections(Session session);
    boolean destroyConnection(Session session, Connection connection);
}
