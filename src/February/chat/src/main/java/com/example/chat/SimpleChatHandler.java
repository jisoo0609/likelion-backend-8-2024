package com.example.chat;

import com.example.chat.dto.ChatMessage;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class SimpleChatHandler extends TextWebSocketHandler {
    // 현재 접속한 사용자 목록
    private final List<WebSocketSession> sessions = new ArrayList<>();
    private final Gson gson = new Gson();

    public void broadcast(ChatMessage message) throws IOException {
        log.info("broadcasting: {}", message);
        for (WebSocketSession session: sessions) {
            session.sendMessage(new TextMessage(gson.toJson(message)));
        }
    }

    // 접속했을때 실행되는 메서드
    @Override
    public void afterConnectionEstablished(
            // WebSocketSession -> 연결된 사용자 한명
            WebSocketSession session
    ) throws Exception {
        // 사용자 저장
        sessions.add(session);
        log.info(session.getId());
        log.info("{} connected, total sessions: {}", session, sessions.size());
    }

    // WebSocket을 통해 메시지를 받으면 실행되는 메서드
    @Override
    protected void handleTextMessage(
            WebSocketSession session,
            TextMessage message
    ) throws Exception {
        // 전달받은 메시지 추출
        String payload = message.getPayload();
        log.info("received: {}, from: {}", payload, session.getId());
        // 접속중인 모든 사용자에게 메시지 전달
        for (WebSocketSession connected: sessions) {
            connected.sendMessage(message);
        }
    }

    // WebSocket 연결이 종료될 때 실행
    @Override
    public void afterConnectionClosed(
            WebSocketSession session,
            CloseStatus status
    ) throws Exception {
        log.info("connection with {} closed", session.getId());
        sessions.remove(session);
    }
}
