package br.com.fiap.gestao.energetica.controller;

import br.com.fiap.gestao.energetica.dto.ConsumoDTO;
import br.com.fiap.gestao.energetica.model.ConsumoEnergia;
import br.com.fiap.gestao.energetica.service.ConsumoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consumos")
@RequiredArgsConstructor
public class ConsumoEnergiaController {
    private final ConsumoService consumoService;

    @GetMapping
    public List<ConsumoEnergia> listar(){
        return consumoService.listar();
    }

    @PostMapping
    public ConsumoEnergia registrarConsumo(@RequestBody ConsumoDTO consumoDTO){
        return consumoService.registrarConsumo(consumoDTO);
    }

    @GetMapping("/historico/{idEquipamento}")
    public List<ConsumoEnergia> historicoConsumo(@PathVariable Long idEquipamento){
        return consumoService.historicoEquipamento(idEquipamento);
    }
}
