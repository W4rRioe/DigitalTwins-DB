package com.DataBase.DigitalTwins.Controllers;

import com.DataBase.DigitalTwins.Backend.Gestao.MonitorDesvioService;
import com.DataBase.DigitalTwins.Backend.Classes.*;
import com.DataBase.DigitalTwins.Backend.Gestao.ViaturaService;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/monitoramento")
public class MonitoramentoController {

    private final MonitorDesvioService monitorService;
    private final ViaturaService viaturaService;

    public MonitoramentoController(MonitorDesvioService monitorService, 
                                ViaturaService viaturaService) {
        this.monitorService = monitorService;
        this.viaturaService = viaturaService;
    }

    @GetMapping("/verificar-desvio/{viaturaId}")
    public ResponseEntity<String> verificarDesvio(@PathVariable Long viaturaId) {
        Viatura viatura = viaturaService.buscarPorId(viaturaId);
        
        if (viatura == null) {
            return ResponseEntity.notFound().build();
        }

        boolean emDesvio = monitorService.verificarDesvio(viatura);
        
        if (emDesvio) {
            return ResponseEntity.ok("{\"status\": \"DESVIO\", \"message\": \"Viatura fora da rota\"}");
        } else {
            return ResponseEntity.ok("{\"status\": \"OK\", \"message\": \"Na rota normal\"}");
        }
    }

    @GetMapping("/viaturas-com-desvio")
    public List<Viatura> listarViaturasComDesvio() {
        return viaturaService.listarTodas().stream()
            .filter(v -> monitorService.verificarDesvio(v))
            .collect(Collectors.toList());
    }
}