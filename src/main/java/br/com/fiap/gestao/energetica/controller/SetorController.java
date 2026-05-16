package br.com.fiap.gestao.energetica.controller;

import br.com.fiap.gestao.energetica.dto.SetorDTO;
import br.com.fiap.gestao.energetica.model.Setor;
import br.com.fiap.gestao.energetica.service.SetorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setores")
@RequiredArgsConstructor
public class SetorController {

    private final SetorService setorService;

    @GetMapping
    public List<Setor> listar() {
        return setorService.listarSetores();
    }

    @PostMapping
    public Setor salvar(@RequestBody @Valid SetorDTO setorDTO) {
        return setorService.salvarSetor(setorDTO);
    }

}
