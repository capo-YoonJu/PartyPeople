package com.ssafy.api.service;

import io.openvidu.java.client.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OpenviduServiceImpl implements OpenviduService{
    OpenVidu openVidu;
    SessionProperties sessionProperties;

    OpenviduServiceImpl(){
        this.openVidu = new OpenVidu("http://partypeople.kro.kr:5443/",
                "a106ssafy0183");
        this.sessionProperties = new SessionProperties.Builder().build();
    }

    @Override
    public Session createSession() {
        try {
            return openVidu.createSession(this.sessionProperties);
        } catch (OpenViduJavaClientException e) {
            e.printStackTrace();
        } catch (OpenViduHttpException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Session> fetchActiveSessions() {
        try {
            openVidu.fetch();
        } catch (OpenViduJavaClientException e) {
            e.printStackTrace();
        } catch (OpenViduHttpException e) {
            e.printStackTrace();
        }
        return openVidu.getActiveSessions();
    }

    @Override
    public boolean closeSession(Session session) {
        try {
            session.close();
        } catch (OpenViduJavaClientException e) {
            e.printStackTrace();
            return false;
        } catch (OpenViduHttpException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String createConnection(Session session) {
        // 연결 성공 시 토큰 반환
        ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
                .type(ConnectionType.WEBRTC)
                .role(OpenViduRole.PUBLISHER)
                .data("user_data")
                .build();
        Connection connection = null;

        try {
            connection = session.createConnection(connectionProperties);
        } catch (OpenViduJavaClientException e) {
            e.printStackTrace();
        } catch (OpenViduHttpException e) {
            e.printStackTrace();
        }

        return connection.getToken(); // Send this string to the client side
    }

    @Override
    public List<Connection> fetchActiveConnections(Session session) {
        try {
            session.fetch();
        } catch (OpenViduJavaClientException e) {
            e.printStackTrace();
        } catch (OpenViduHttpException e) {
            e.printStackTrace();
        }
        return session.getActiveConnections();
    }

    @Override
    public boolean destroyConnection(Session session, Connection connection) {
        try {
            session.forceDisconnect(connection);
        } catch (OpenViduJavaClientException e) {
            e.printStackTrace();
            return false;
        } catch (OpenViduHttpException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
