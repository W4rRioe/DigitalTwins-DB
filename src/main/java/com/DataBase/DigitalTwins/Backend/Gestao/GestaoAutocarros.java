package com.DataBase.DigitalTwins.Backend.Gestao;

import lombok.Getter;
import org.springframework.stereotype.Service;

import com.DataBase.DigitalTwins.Backend.Classes.Viatura;

import java.util.ArrayList;
import java.util.List;

@Service
public class GestaoAutocarros {

    // Lista estática de exemplo de viaturas
    @Getter
    private static final List<Viatura> viaturas = new ArrayList<>();
    private static final Double NULL = null;

    static {
        // Adicionando viaturas de exemplo (com base nos campos da classe Viatura)
        viaturas.add(new Viatura(1L, "34-AB-56", 2020, "Regular", 41.55032, -8.42005, 45.0, 40, 75.5, Viatura.StatusOperacional.EM_SERVICO, null, null, null, null));
        viaturas.add(new Viatura(2L, "99-ZZ-01", 2022, "Expresso", 41.55123, -8.42256, 80.0, 50, 90.0, Viatura.StatusOperacional.FORA_DE_SERVICO, null, null, null, null));
        viaturas.add(new Viatura(3L, "12-CD-34", 2019, "Noturno", 41.54567, -8.42678, 38.5, 25, 60.0, Viatura.StatusOperacional.MANUTENCAO, null, null, null, null));
        viaturas.add(new Viatura(4L, "78-XY-45", 2018, "Turístico", 41.54321, -8.41987, 50.0, 30, NULL, Viatura.StatusOperacional.EM_SERVICO, null, null, null, null));
        viaturas.add(new Viatura(5L, "55-YY-77", 2023, "Regular", 41.55234, -8.43056, 60.0, 45, 85.0, Viatura.StatusOperacional.EM_SERVICO, null, null, null, null));
    }

    // Método para verificar o estado da bateria
    public String verificarEstadoBateria(Long id) {
        Viatura viatura = encontrarViaturaPorId(id);
        
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

    // Método para simular a busca da viatura pelo ID
    public Viatura encontrarViaturaPorId(Long id) {
        return viaturas.stream()
                .filter(viatura -> viatura.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Autocarro não encontrado com ID: " + id));
    }

    public List<Viatura> listarViaturas() {
        throw new UnsupportedOperationException("Unimplemented method 'listarViaturas'");
    }
}
