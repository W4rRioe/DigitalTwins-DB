package com.DataBase.DigitalTwins.Backend.Bateria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstacaoCarregamento {
    private Long id;
    private String nome;
    private double latitude;
    private double longitude;
    private double distancia;
    private boolean disponivel;
    private int tempoEstimadoCarregamento;
}