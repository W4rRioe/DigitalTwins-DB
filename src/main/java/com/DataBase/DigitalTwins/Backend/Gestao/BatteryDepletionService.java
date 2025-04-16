package com.DataBase.DigitalTwins.Backend.Gestao;

import com.DataBase.DigitalTwins.Backend.Classes.Viatura;
import com.DataBase.DigitalTwins.Repositorios.ViaturaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@EnableScheduling
@Slf4j
public class BatteryDepletionService {

    private final ViaturaRepository viaturaRepository;
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public BatteryDepletionService(ViaturaRepository viaturaRepository) {
        this.viaturaRepository = viaturaRepository;
    }

    @Scheduled(fixedRate = 60000) // 60 segundos
    @Transactional
    public void simularGastoBateria() {
        log.debug("Executando simulação automática de gasto de bateria");
        List<Viatura> viaturas = viaturaRepository.findByStatusOperacional(Viatura.StatusOperacional.EM_SERVICO);
        viaturas.forEach(this::diminuirBateria);
    }

    private void diminuirBateria(Viatura viatura) {
        Double nivelAtual = viatura.getNivelEnergia();
        
        if (nivelAtual == null || nivelAtual <= 0) {
            return;
        }

        double taxaBase = 0.05;
        double taxaVelocidade = calcularTaxaVelocidade(viatura);
        double variacaoAleatoria = random.nextDouble(-0.02, 0.02);
        
        double taxaTotal = taxaBase + taxaVelocidade + variacaoAleatoria;
        double novoNivel = Math.max(0, nivelAtual - taxaTotal);
        double nivelArredondado = Math.round(novoNivel * 10.0) / 10.0;
        
        viatura.setNivelEnergia(nivelArredondado);
        viaturaRepository.save(viatura);
        
        verificarNivelCritico(viatura, nivelArredondado);
    }
    
    private double calcularTaxaVelocidade(Viatura viatura) {
        if (viatura.getVelocidade() == null) {
            return 0;
        }
        return (viatura.getVelocidade() / 100.0) * 0.15;
    }
    
    private void verificarNivelCritico(Viatura viatura, double nivel) {
        if (nivel < 10.0) {
            log.warn("ALERTA: Bateria crítica para viatura {} - {}%", viatura.getMatricula(), nivel);
            
            if (nivel < 5.0) {
                viatura.setStatusOperacional(Viatura.StatusOperacional.FORA_DE_SERVICO);
                viaturaRepository.save(viatura);
                log.warn("Viatura {} colocada FORA DE SERVIÇO por bateria crítica", viatura.getMatricula());
            }
        }
    }

    @Transactional
    public void recarregarBateria(Long viaturaId, double quantidade) {
        try {
            Viatura viatura = viaturaRepository.findById(viaturaId)
                .orElseThrow(() -> new RuntimeException("Viatura não encontrada com ID: " + viaturaId));
            
            Double nivelAtual = viatura.getNivelEnergia() != null ? viatura.getNivelEnergia() : 0.0;
            double novoNivel = Math.min(100.0, nivelAtual + quantidade);
            
            viatura.setNivelEnergia(novoNivel);
            viaturaRepository.save(viatura);
            
            log.info("Bateria da viatura {} recarregada para {}%", viatura.getMatricula(), novoNivel);
        } catch (Exception e) {
            log.error("Erro ao recarregar bateria da viatura {}: {}", viaturaId, e.getMessage());
            throw e;
        }
    }
}