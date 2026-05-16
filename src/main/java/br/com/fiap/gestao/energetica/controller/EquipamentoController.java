package br.com.fiap.gestao.energetica.controller;

import br.com.fiap.gestao.energetica.dto.EquipamentoDTO;
import br.com.fiap.gestao.energetica.model.Equipamento;
import br.com.fiap.gestao.energetica.service.EquipamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipamentos")
@RequiredArgsConstructor
public class EquipamentoController {

    private final EquipamentoService equipamentoService;

    @GetMapping
    public List<Equipamento> listarEquipamentos() {
        return equipamentoService.listarEquipamentos();
    }

    @GetMapping("/{id}")
    public Equipamento buscarPorId(@PathVariable Long id) {
        return equipamentoService.buscarPorId(id);
    }

    @PostMapping
    public Equipamento salvarEquipamento(
            @RequestBody EquipamentoDTO equipamentoDTO
    ) {
        return equipamentoService.salvarEquipamento(equipamentoDTO);
    }

    @PutMapping("/{id}")
    public Equipamento atualizarEquipamento(
            @PathVariable Long id,
            @RequestBody EquipamentoDTO equipamentoDTO
    ) {
        return equipamentoService.atualizarEquipamento(id, equipamentoDTO);
    }

    @DeleteMapping("/{id}")
    public void deletarEquipamento(@PathVariable Long id) {
        equipamentoService.deletarEquipamento(id);
    }

    @GetMapping("/ligados")
    public List<Equipamento> listarLigados() {
        return equipamentoService.listarEquipamentosLigados();
    }

    @GetMapping("/alto-consumo")
    public List<Equipamento> listarAltoConsumo() {
        return equipamentoService.listarAltoConsumo();
    }

    @PutMapping("/desligar/{id}")
    public Equipamento desligarEquipamento(
            @PathVariable Long id
    ) {
        return equipamentoService.desligarEquipamento(id);
    }
}