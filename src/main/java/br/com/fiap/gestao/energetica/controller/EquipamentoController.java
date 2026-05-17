package br.com.fiap.gestao.energetica.controller;

import br.com.fiap.gestao.energetica.dto.EquipamentoDTO;
import br.com.fiap.gestao.energetica.model.Equipamento;
import br.com.fiap.gestao.energetica.service.EquipamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Equipamentos",
description = "Endpoints de gerenciamento de equipamentos")
@RestController
@RequestMapping("/equipamentos")
@RequiredArgsConstructor
public class EquipamentoController {

    private final EquipamentoService equipamentoService;

    @Operation(summary = "Listar todos os equipamentos")
    @GetMapping
    public List<Equipamento> listarEquipamentos() {
        return equipamentoService.listarEquipamentos();
    }

    @Operation(summary = "buscar equipamento por Id")
    @GetMapping("/{id}")
    public Equipamento buscarPorId(@PathVariable Long id) {
        return equipamentoService.buscarPorId(id);
    }

    @Operation(summary = "Cadastrar equipamento")
    @PostMapping
    public Equipamento salvarEquipamento(
            @RequestBody EquipamentoDTO equipamentoDTO
    ) {
        return equipamentoService.salvarEquipamento(equipamentoDTO);
    }

    @Operation(summary = "Atualizar equipamento")
    @PutMapping("/{id}")
    public Equipamento atualizarEquipamento(
            @PathVariable Long id,
            @RequestBody EquipamentoDTO equipamentoDTO
    ) {
        return equipamentoService.atualizarEquipamento(id, equipamentoDTO);
    }

    @Operation(summary = "Deletar equipamento")
    @DeleteMapping("/{id}")
    public void deletarEquipamento(@PathVariable Long id) {
        equipamentoService.deletarEquipamento(id);
    }

    @Operation(summary = "Consulta equipamentos ligados")
    @GetMapping("/ligados")
    public List<Equipamento> listarLigados() {
        return equipamentoService.listarEquipamentosLigados();
    }

    @Operation(summary = "Consulta equipamentos em alto consumo")
    @GetMapping("/alto-consumo")
    public List<Equipamento> listarAltoConsumo() {
        return equipamentoService.listarAltoConsumo();
    }

    @Operation(summary = "Desliga equipamento")
    @PutMapping("/desligar/{id}")
    public Equipamento desligarEquipamento(
            @PathVariable Long id
    ) {
        return equipamentoService.desligarEquipamento(id);
    }
}