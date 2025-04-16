package com.DataBase.DigitalTwins.Controllers;

import org.springframework.web.bind.annotation.*;
import com.DataBase.DigitalTwins.Backend.Classes.Viatura;
import com.DataBase.DigitalTwins.Backend.Gestao.BatteryDepletionService;
import com.DataBase.DigitalTwins.Backend.Gestao.GestaoAutocarros;
import com.DataBase.DigitalTwins.Backend.Gestao.ViaturaService;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/viaturas")
public class ViaturaController {

    private final ViaturaService viaturaService;
    private final GestaoAutocarros gestaoAutocarros;
    private final BatteryDepletionService batteryDepletionService;

    public ViaturaController(ViaturaService viaturaService, 
                           GestaoAutocarros gestaoAutocarros, 
                           BatteryDepletionService batteryDepletionService) {
        this.viaturaService = viaturaService;
        this.gestaoAutocarros = gestaoAutocarros;
        this.batteryDepletionService = batteryDepletionService;
    }

    @GetMapping
    public List<Viatura> listarViaturas() {
        return viaturaService.listarTodas();
    }

    @GetMapping("/{id}")
    public Viatura obterViaturaPorId(@PathVariable Long id) {
        return gestaoAutocarros.encontrarViaturaPorId(id);
    }

    @GetMapping("/{id}/bateria")
    public String verificarEstadoBateria(@PathVariable Long id) {
        return gestaoAutocarros.verificarEstadoBateria(id);
    }

    @PostMapping("/{id}/recarregar")
    public ResponseEntity<String> recarregarBateria(
            @PathVariable Long id,
            @RequestParam(defaultValue = "100.0") double quantidade) {
        try {
            batteryDepletionService.recarregarBateria(id, quantidade);
            return ResponseEntity.ok("Bateria recarregada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao recarregar: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Viatura> atualizarViatura(@PathVariable Long id, @RequestBody Viatura viaturaAtualizada) {
        Viatura viaturaExistente = gestaoAutocarros.encontrarViaturaPorId(id);

        if (viaturaExistente != null) {
            viaturaExistente.setMatricula(viaturaAtualizada.getMatricula());
            viaturaExistente.setAnoFabricacao(viaturaAtualizada.getAnoFabricacao());
            viaturaExistente.setTipoServico(viaturaAtualizada.getTipoServico());
            viaturaExistente.setLatitude(viaturaAtualizada.getLatitude());
            viaturaExistente.setLongitude(viaturaAtualizada.getLongitude());
            viaturaExistente.setNivelEnergia(viaturaAtualizada.getNivelEnergia());
            viaturaExistente.setOcupacao(viaturaAtualizada.getOcupacao());
            viaturaExistente.setVelocidade(viaturaAtualizada.getVelocidade());
            viaturaExistente.setStatusOperacional(viaturaAtualizada.getStatusOperacional());
            
            return ResponseEntity.ok(viaturaExistente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}