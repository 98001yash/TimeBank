package com.company.TimeBank.service;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MessageWebSocketHandler extends TextWebSocketHandler {

    //
    private Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connection established: " + session.getId());

        String userId = (String) session.getAttributes().get("userId");
        if (userId != null) {
            userSessions.put(userId, session);
            System.out.println("User " + userId + " connected.");
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println("Received message from user " + session.getId() + ": " + message.getPayload());
        String payload = message.getPayload().toString();
        String[] messageParts = payload.split(":");

        if (messageParts.length < 2) {
            session.sendMessage(new TextMessage("Invalid message format. Expected format: userId:message"));
            return;
        }

        String recipientId = messageParts[0];
        String actualMessage = messageParts[1];

        WebSocketSession recipientSession = userSessions.get(recipientId);

        if (recipientSession != null) {
            recipientSession.sendMessage(new TextMessage("Message from " + session.getId() + ": " + actualMessage));
        } else {
            session.sendMessage(new TextMessage("User " + recipientId + " is not connected or does not exist"));
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("Error with session " + session.getId() + ": " + exception.getMessage());
        String userId = (String) session.getAttributes().get("userId");
        if (userId != null) {
            userSessions.remove(userId);
            System.out.println("Removed user " + userId + " from active sessions due to error.");
        }

        session.close(CloseStatus.SERVER_ERROR);
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("Connection closed: " + session.getId());

        String userId = (String) session.getAttributes().get("userId");
        if (userId != null) {
            userSessions.remove(userId);
            System.out.println("User " + userId + " disconnected.");
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
