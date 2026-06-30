package br.com.fiap.gestao.energetica.controller;

import br.com.fiap.gestao.energetica.dto.EmpresaDTO;
import br.com.fiap.gestao.energetica.model.Empresa;
import br.com.fiap.gestao.energetica.service.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Empresas",
        description = "Endpoints de gerenciamento de empresas")
@RestController
@RequestMapping("/empresas")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaService empresaService;

    @Operation(summary = "Listar empresas")
    @GetMapping
    public List<Empresa> listar() {
        return empresaService.listarEmpresas();
    }

    @Operation(summary = "Salvar empresas")
    @PostMapping
    public Empresa salvar(
            @RequestBody
            @Valid EmpresaDTO empresaDTO
    ) {

        return empresaService.salvarEmpresa(empresaDTO);
    }

    @Operation(summary = "Atualizar empresas")
    @PutMapping("/{id}")
    public Empresa atualizar(
            @PathVariable Long id,
            @RequestBody
            @Valid EmpresaDTO empresaDTO
    ) {

        return empresaService.atualizarEmpresa(id, empresaDTO);
    }

    @Operation(summary = "Deletar empresas")
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {

        empresaService.deletarEmpresa(id);
    }
}