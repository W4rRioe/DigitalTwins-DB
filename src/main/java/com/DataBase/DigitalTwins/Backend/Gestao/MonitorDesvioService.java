package com.DataBase.DigitalTwins.Backend.Gestao;

import com.DataBase.DigitalTwins.Backend.Classes.*;
import org.springframework.stereotype.Service;

@Service
public class MonitorDesvioService {

    private static final double LIMITE_DESVIO_KM = 0.05; // 50 metros

    public boolean verificarDesvio(Viatura viatura) {
        if (viatura.getViaAtual() == null) return false;
        
        return viatura.getViaAtual().getPontos().stream()
            .noneMatch(ponto -> 
                calcularDistancia(
                    viatura.getLatitude(), viatura.getLongitude(),
                    ponto.getLatitude(), ponto.getLongitude()
                ) <= LIMITE_DESVIO_KM
            );
    }

    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        double dx = 111.32 * (lon2 - lon1) * Math.cos((lat1 + lat2)/2);
        double dy = 111.32 * (lat2 - lat1);
        return Math.sqrt(dx*dx + dy*dy);
    }
}