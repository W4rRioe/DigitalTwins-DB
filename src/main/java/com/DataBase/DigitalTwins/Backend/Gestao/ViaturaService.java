package com.DataBase.DigitalTwins.Backend.Gestao;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.DataBase.DigitalTwins.Backend.Classes.Viatura;
import com.DataBase.DigitalTwins.Repositorios.ViaturaRepository;

@Service
public class ViaturaService {

    @Autowired
    private ViaturaRepository viaturaRepository;

    // Busca viatura por matrícula
    public Viatura getViaturaByMatricula(String matricula) {
        return viaturaRepository.findByMatricula(matricula).orElse(null);
    }

    // Busca viatura por ID
    public Viatura buscarPorId(Long viaturaId) {
        return viaturaRepository.findById(viaturaId).orElse(null);
    }

    // Lista todas as viaturas
    public List<Viatura> listarTodas() {
        return viaturaRepository.findAll(); // JpaRepository já retorna List
    }

    // Lista viaturas em serviço
    public List<Viatura> listarEmServico() {
        return viaturaRepository.findAll().stream()
            .filter(v -> v.getStatusOperacional() == Viatura.StatusOperacional.EM_SERVICO)
            .collect(Collectors.toList());
    }

    // Lista viaturas com desvio (usando o serviço de monitorização)
    public List<Viatura> listarComDesvio(MonitorDesvioService monitorService) {
        return listarEmServico().stream()
            .filter(v -> monitorService.verificarDesvio(v))
            .collect(Collectors.toList());
    }

    // Atualiza posição da viatura
    public Viatura atualizarPosicao(Long viaturaId, Double latitude, Double longitude) {
        Viatura viatura = buscarPorId(viaturaId);
        if (viatura != null) {
            viatura.setLatitude(latitude);
            viatura.setLongitude(longitude);
            return viaturaRepository.save(viatura);
        }
        return null;
    }
}