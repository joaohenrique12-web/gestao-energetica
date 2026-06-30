package br.com.fiap.gestao.energetica.controller;

import br.com.fiap.gestao.energetica.model.Alerta;
import br.com.fiap.gestao.energetica.service.AlertaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Alertas",
        description = "Endpoints de gerenciamento de alertas")
@RestController
@RequestMapping("/alertas")
@RequiredArgsConstructor
public class AlertaController {
    private final AlertaService alertaService;

    @Operation(summary = "Listar alertas")
    @GetMapping
    public List<Alerta> listarAlertas(){
        return alertaService.listar();
    }

    @Operation(summary = "Listar alertas recentes")
    @GetMapping("/recentes")
    public List<Alerta> recentes(){
        return alertaService.listarAlertasRecentes();
    }
}
