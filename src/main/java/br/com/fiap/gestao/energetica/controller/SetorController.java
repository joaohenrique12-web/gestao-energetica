package br.com.fiap.gestao.energetica.controller;

import br.com.fiap.gestao.energetica.dto.SetorDTO;
import br.com.fiap.gestao.energetica.model.Setor;
import br.com.fiap.gestao.energetica.service.SetorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Setores",
        description = "Endpoints de gerenciamento de setores")
@RestController
@RequestMapping("/setores")
@RequiredArgsConstructor
public class SetorController {

    private final SetorService setorService;

    @GetMapping
    @Operation(summary = "Listar setores")
    public List<Setor> listar() {
        return setorService.listarSetores();
    }

    @PostMapping
    @Operation(summary = "Salvar setor")
    public Setor salvar(@RequestBody @Valid SetorDTO setorDTO) {
        return setorService.salvarSetor(setorDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar setor")
    public Setor atualizar(
            @PathVariable Long id,
            @RequestBody @Valid SetorDTO setorDTO
    ) {

        return setorService.atualizarSetor(id, setorDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar setor")
    public void deletar(@PathVariable Long id) {

        setorService.deletarSetor(id);
    }
}