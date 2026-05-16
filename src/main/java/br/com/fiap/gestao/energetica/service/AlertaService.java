package br.com.fiap.gestao.energetica.service;

import br.com.fiap.gestao.energetica.model.Alerta;
import br.com.fiap.gestao.energetica.repository.AlertaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertaService {
    private final AlertaRepository alertaRepository;

    public List<Alerta> listar(){
        return alertaRepository.findAll();
    }

    public List<Alerta> listarAlertasRecentes(){
        return alertaRepository.findAll()
                .stream()
                .sorted((a1, a2) ->
                        a2.getDataHoraAlerta().compareTo(a1.getDataHoraAlerta()))
                .limit(5)
                .toList();
    }
}
