package com.DataBase.DigitalTwins.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.DataBase.DigitalTwins.Backend.Classes.Viatura;
import com.DataBase.DigitalTwins.Backend.Gestao.GestaoAutocarros;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@SuppressWarnings("unused")
@Controller
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private GestaoAutocarros gestaoAutocarros;

    @MessageMapping("/viaturas/listar")
    @SendTo("/topic/viaturas")
    public List<Viatura> listarViaturas() {
        return GestaoAutocarros.getViaturas();
    }

    @MessageMapping("/viaturas/estado-bateria")
    public void verificarEstadoBateria(Long viaturaId) {
        try {
            String estadoBateria = gestaoAutocarros.verificarEstadoBateria(viaturaId);
            
            // Preparar objeto de resposta
            Map<String, Object> response = Map.of(
                "id", viaturaId,
                "estado", estadoBateria
            );

            // Enviar para o tópico de estado de bateria
            messagingTemplate.convertAndSend("/topic/estado-bateria", response);
        } catch (Exception e) {
            // Tratar erros
            Map<String, Object> errorResponse = Map.of(
                "id", viaturaId,
                "estado", "Erro ao verificar bateria: " + e.getMessage()
            );
            messagingTemplate.convertAndSend("/topic/estado-bateria", errorResponse);
        }
    }
}