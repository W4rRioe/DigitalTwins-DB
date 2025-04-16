package com.DataBase.DigitalTwins.Backend.Gestao;

import com.DataBase.DigitalTwins.Backend.Classes.Viatura;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@EnableScheduling
@Slf4j
public class BatteryDepletionService {

    @Autowired
    private GestaoAutocarros gestaoAutocarros;
    
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    /**
     * Executa automaticamente a simulação de gasto de bateria a cada 60 segundos
     */
    @Scheduled(fixedRate = 60000) // 60 segundos
    public void simularGastoBateria() {
        log.debug("Executando simulação automática de gasto de bateria");
        List<Viatura> viaturas = GestaoAutocarros.getViaturas();
        viaturas.stream()
            .filter(v -> v.getStatusOperacional() == Viatura.StatusOperacional.EM_SERVICO)
            .forEach(this::diminuirBateria);
    }

    /**
     * Diminui o nível de bateria baseado no estado operacional e velocidade
     */
    private void diminuirBateria(Viatura viatura) {
        Double nivelAtual = viatura.getNivelEnergia();
        
        if (nivelAtual == null || nivelAtual <= 0) {
            return;
        }

        // Cálculo da taxa de consumo
        double taxaBase = 0.05; // Base: 0.05% por minuto
        double taxaVelocidade = calcularTaxaVelocidade(viatura);
        double variacaoAleatoria = random.nextDouble(-0.02, 0.02); // +/- 0.02%
        
        double taxaTotal = taxaBase + taxaVelocidade + variacaoAleatoria;
        double novoNivel = Math.max(0, nivelAtual - taxaTotal);
        
        // Arredonda para uma casa decimal
        double nivelArredondado = Math.round(novoNivel * 10.0) / 10.0;
        atualizarNivelBateria(viatura, nivelArredondado);
        
        // Tratamento de nível crítico
        verificarNivelCritico(viatura, nivelArredondado);
    }
    
    /**
     * Calcula taxa de consumo baseada na velocidade
     */
    private double calcularTaxaVelocidade(Viatura viatura) {
        if (viatura.getVelocidade() == null) {
            return 0;
        }
        return (viatura.getVelocidade() / 100.0) * 0.15;
    }
    
    /**
     * Verifica se o nível de bateria está em estado crítico
     */
    private void verificarNivelCritico(Viatura viatura, double nivel) {
        if (nivel < 10.0) {
            log.warn("ALERTA: Bateria crítica para viatura {} - {}%", viatura.getMatricula(), nivel);
            
            if (nivel < 5.0) {
                viatura.setStatusOperacional(Viatura.StatusOperacional.FORA_DE_SERVICO);
                log.warn("Viatura {} colocada FORA DE SERVIÇO por bateria crítica", viatura.getMatricula());
            }
        }
    }

    /**
     * Atualiza o nível de bateria da viatura
     */
    private void atualizarNivelBateria(Viatura viatura, double novoNivel) {
        try {
            viatura.setNivelEnergia(novoNivel);
        } catch (Exception e) {
            log.error("Erro ao atualizar nível de bateria da viatura {}: {}", viatura.getId(), e.getMessage());
        }
    }
    
    /**
     * Recarrega a bateria de uma viatura
     */
    public void recarregarBateria(Long viaturaId, double quantidade) {
        try {
            Viatura viatura = gestaoAutocarros.encontrarViaturaPorId(viaturaId);
            Double nivelAtual = viatura.getNivelEnergia() != null ? viatura.getNivelEnergia() : 0.0;
        
            // Limita o nível máximo a 100%
            double novoNivel = Math.min(100.0, nivelAtual + quantidade);
            atualizarNivelBateria(viatura, novoNivel);
        
            log.info("Bateria da viatura {} recarregada para {}%", viatura.getMatricula(), novoNivel);
        } catch (Exception e) {
            log.error("Erro ao recarregar bateria da viatura {}: {}", viaturaId, e.getMessage());
        }
    }
}