package com.DataBase.DigitalTwins.Backend.Bateria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RotaOtimizada {
    private Long viaturaId;
    private String matricula;
    private List<PontoRota> pontosRota;
    private double distanciaTotal;
    private double consumoEstimado;
    private double economiaEnergetica;
}