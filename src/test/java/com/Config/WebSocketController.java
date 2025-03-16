package com.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Envia uma mensagem para o tópico /topic/messages
    @MessageMapping("/send") // Recebe mensagens do frontend
    public void sendMessage(String message) {
        messagingTemplate.convertAndSend("/topic/messages", message); // Envia para o frontend
    }
}
