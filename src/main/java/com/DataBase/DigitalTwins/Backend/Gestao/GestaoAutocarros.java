package com.DataBase.DigitalTwins.Backend.Gestao;

import com.DataBase.DigitalTwins.Backend.Classes.Viatura;
import com.DataBase.DigitalTwins.Repositorios.ViaturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GestaoAutocarros {

    private final ViaturaRepository viaturaRepository;

    // Injeção de dependência do repositório
    public GestaoAutocarros(ViaturaRepository viaturaRepository) {
        this.viaturaRepository = viaturaRepository;
    }

    // Método para verificar o estado da bateria
    public String verificarEstadoBateria(Long id) {
        Viatura viatura = encontrarViaturaPorId(id);
        
        if (viatura.getNivelEnergia() == null) {
            throw new RuntimeException("Nível de energia não encontrado para este veículo");
        }

        String statusBateria = determinarStatusBateria(viatura.getNivelEnergia());
        
        if (statusBateria.equals("CRÍTICO")) {
            return "Atenção! O nível de bateria está CRÍTICO. Por favor, recarregue o veículo imediatamente.";
        } else {
            return "O nível de bateria está " + statusBateria + ".";
        }
    }

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

    // Método para buscar viatura pelo ID
    public Viatura encontrarViaturaPorId(Long id) {
        Optional<Viatura> viaturaOptional = viaturaRepository.findById(id);
        return viaturaOptional.orElseThrow(() -> 
            new RuntimeException("Autocarro não encontrado com ID: " + id));
    }

    // Método para listar todas as viaturas
    public List<Viatura> listarViaturas() {
        return viaturaRepository.findAll();
    }

    // Método adicional para buscar por matrícula
    public Viatura encontrarViaturaPorMatricula(String matricula) {
        return viaturaRepository.findByMatricula(matricula)
                .orElseThrow(() -> 
                    new RuntimeException("Autocarro não encontrado com matrícula: " + matricula));
    }

    // Método para salvar/atualizar uma viatura
    public Viatura salvarViatura(Viatura viatura) {
        return viaturaRepository.save(viatura);
    }

    // Método para deletar uma viatura
    public void deletarViatura(Long id) {
        viaturaRepository.deleteById(id);
    }
}