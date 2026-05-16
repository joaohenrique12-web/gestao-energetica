package br.com.fiap.gestao.energetica.controller;

import br.com.fiap.gestao.energetica.model.Alerta;
import br.com.fiap.gestao.energetica.service.AlertaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/alertas")
@RequiredArgsConstructor
public class AlertaController {
    private final AlertaService alertaService;

    @GetMapping
    public List<Alerta> listarAlertas(){
        return alertaService.listar();
    }

    @GetMapping("/recentes")
    public List<Alerta> recentes(){
        return alertaService.listarAlertasRecentes();
    }
}
