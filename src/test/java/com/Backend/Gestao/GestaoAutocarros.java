package com.Backend.Gestao;

import com.Backend.Classes.Viatura;
import com.Repositorios.ViaturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GestaoAutocarros {

    private final ViaturaRepository viaturaRepository;

    // Método para verificar o estado da bateria
    public String verificarEstadoBateria(Long id) {
        Viatura viatura = viaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autocarro não encontrado com ID: " + id));
        
        // Verifica se o nível de energia é nulo ou inválido
        if (viatura.getNivelEnergia() == null) {
            throw new RuntimeException("Nível de energia não encontrado para este veículo");
        }

        // Determina o estado da bateria
        String statusBateria = determinarStatusBateria(viatura.getNivelEnergia());
        
        // Se o status for crítico, emite uma mensagem
        if (statusBateria.equals("CRÍTICO")) {
            return "Atenção! O nível de bateria está CRÍTICO. Por favor, recarregue o veículo imediatamente.";
        } else {
            return "O nível de bateria está " + statusBateria + ".";
        }
    }

    // Método para determinar o status da bateria com base no nível de energia
    private String determinarStatusBateria(double nivelEnergia) {
        if (nivelEnergia < 10.0) {
            return "CRÍTICO";
        } else if (nivelEnergia < 20.0) {
            return "MUITO BAIXO";
        } else if (nivelEnergia < 50.0) {
            return "MÉDIO";
        } else {
            return "BOM";
        }
    }
}
