package br.com.fiap.gestao.energetica.controller;

import br.com.fiap.gestao.energetica.dto.ConsumoDTO;
import br.com.fiap.gestao.energetica.model.ConsumoEnergia;
import br.com.fiap.gestao.energetica.service.ConsumoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Consumos",
        description = "Endpoints de gerenciamento de consumos")
@RestController
@RequestMapping("/consumos")
@RequiredArgsConstructor
public class ConsumoEnergiaController {
    private final ConsumoService consumoService;

    @Operation(summary = "Listar Consumo")
    @GetMapping
    public List<ConsumoEnergia> listar(){
        return consumoService.listar();
    }

    @Operation(summary = "Registar Consumo")
    @PostMapping
    public ConsumoEnergia registrarConsumo(@RequestBody ConsumoDTO consumoDTO){
        return consumoService.registrarConsumo(consumoDTO);
    }

    @Operation(summary = "listar histórico de insumo")
    @GetMapping("/historico/{idEquipamento}")
    public List<ConsumoEnergia> historicoConsumo(@PathVariable Long idEquipamento){
        return consumoService.historicoEquipamento(idEquipamento);
    }
}
