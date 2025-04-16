package com.DataBase.DigitalTwins.Controllers;

import org.springframework.web.bind.annotation.*;

import com.DataBase.DigitalTwins.Backend.Classes.Viatura;
import com.DataBase.DigitalTwins.Backend.Gestao.BatteryDepletionService;
import com.DataBase.DigitalTwins.Backend.Gestao.GestaoAutocarros;

import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/viaturas")
public class ViaturaController {

    private final GestaoAutocarros gestaoAutocarros;
    private final BatteryDepletionService batteryDepletionService;

    public ViaturaController(GestaoAutocarros gestaoAutocarros, BatteryDepletionService batteryDepletionService) {
        this.gestaoAutocarros = gestaoAutocarros;
        this.batteryDepletionService = batteryDepletionService;
    }

    @GetMapping
    public List<Viatura> listarViaturas() {
        return GestaoAutocarros.getViaturas();
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

    // Método PUT para atualizar uma viatura
    @PutMapping("/{id}")
    public ResponseEntity<Viatura> atualizarViatura(@PathVariable Long id, @RequestBody Viatura viaturaAtualizada) {
        Viatura viaturaExistente = gestaoAutocarros.encontrarViaturaPorId(id);

        if (viaturaExistente != null) {
            // Atualiza os campos da viatura existente com os dados recebidos
            viaturaExistente.setMatricula(viaturaAtualizada.getMatricula());
            viaturaExistente.setAnoFabricacao(viaturaAtualizada.getAnoFabricacao());
            viaturaExistente.setTipoServico(viaturaAtualizada.getTipoServico());
            viaturaExistente.setLatitude(viaturaAtualizada.getLatitude());
            viaturaExistente.setLongitude(viaturaAtualizada.getLongitude());
            viaturaExistente.setNivelEnergia(viaturaAtualizada.getNivelEnergia());
            viaturaExistente.setOcupacao(viaturaAtualizada.getOcupacao());
            viaturaExistente.setVelocidade(viaturaAtualizada.getVelocidade());
            viaturaExistente.setStatusOperacional(viaturaAtualizada.getStatusOperacional());
            // Adicione outros campos que precisam ser atualizados

            // Retorna a viatura atualizada com status 200 OK
            return ResponseEntity.ok(viaturaExistente);
        } else {
            // Se a viatura não for encontrada, retorna status 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }
}