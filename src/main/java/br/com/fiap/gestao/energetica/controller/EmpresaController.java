package br.com.fiap.gestao.energetica.controller;

import br.com.fiap.gestao.energetica.dto.EmpresaDTO;
import br.com.fiap.gestao.energetica.model.Empresa;
import br.com.fiap.gestao.energetica.service.EmpresaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
@RequiredArgsConstructor
public class EmpresaController {
    private final EmpresaService empresaService;

    @GetMapping
    public List<Empresa> listar() {
        return empresaService.listarEmpresas();
    }

    @PostMapping
    public Empresa salvar(@RequestBody
                          @Valid EmpresaDTO empresaDTO) {
        return  empresaService.salvarEmpresa(empresaDTO);
    }
}
