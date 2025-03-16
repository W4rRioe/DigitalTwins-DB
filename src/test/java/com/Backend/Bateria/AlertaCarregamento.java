package com.Backend.Bateria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertaCarregamento {
    private Long viaturaId;
    private String matricula;
    private double nivelBateria;
    private double autonomiaRestante;
    private LocalDateTime horaAlerta;
    private String mensagem;
    private List<EstacaoCarregamento> estacoesProximas;
    private boolean critico;
}