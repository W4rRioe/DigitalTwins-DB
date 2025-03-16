package com.Backend.Bateria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BateriaStatus {
    private Long viaturaId;
    private String matricula;
    private double nivelBateria;
    private double autonomiaEstimada;
    private double tempoRestante;
    private String statusBateria;
    private LocalDateTime ultimaAtualizacao;
}