package br.com.fiap.gestao.energetica.service;

import br.com.fiap.gestao.energetica.dto.ConsumoDTO;
import br.com.fiap.gestao.energetica.model.Alerta;
import br.com.fiap.gestao.energetica.model.ConsumoEnergia;
import br.com.fiap.gestao.energetica.model.Equipamento;
import br.com.fiap.gestao.energetica.repository.AlertaRepository;
import br.com.fiap.gestao.energetica.repository.ConsumoEnergiaRepository;
import br.com.fiap.gestao.energetica.repository.EquipamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsumoService {

    private final ConsumoEnergiaRepository consumoEnergiaRepository;
    private final EquipamentoRepository equipamentoRepository;
    private final AlertaRepository alertaRepository;

    public List<ConsumoEnergia> listar(){
        return consumoEnergiaRepository.findAll();
    }

    public ConsumoEnergia registrarConsumo(ConsumoDTO consumoDTO){

        Equipamento equipamento = equipamentoRepository.findById(consumoDTO.getIdEquipamento())
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado!"));

        ConsumoEnergia consumoEnergia = new ConsumoEnergia();

        consumoEnergia.setEquipamento(equipamento);
        consumoEnergia.setConsumoKwh(consumoDTO.getConsumoKwh());
        consumoEnergia.setDataHora(LocalDateTime.now());
        equipamento.setConsumoWatts(consumoDTO.getConsumoKwh());
        consumoEnergia.setDataHora(LocalDateTime.now());

        // REGRA DE STATUS

        if (consumoDTO.getConsumoKwh() == 0){

            equipamento.setStatus("DESLIGADO");

            // ALERTA AUTOMÁTICO

            Alerta alerta = new Alerta();

            alerta.setMensagem("Equipamento desligado.");
            alerta.setDataHoraAlerta(LocalDateTime.now());

            // FALTAVA ISSO
            alerta.setEquipamento(equipamento);

            alertaRepository.save(alerta);

        } else {

            equipamento.setStatus("LIGADO");
        }

        // ALERTA DE ALTO CONSUMO

        if (consumoDTO.getConsumoKwh() > 700){

            Alerta alerta = new Alerta();

            alerta.setMensagem("ALERTA: Alto consumo de energia detectado.");
            alerta.setDataHoraAlerta(LocalDateTime.now());

            // FALTAVA ISSO
            alerta.setEquipamento(equipamento);

            alertaRepository.save(alerta);
        }

        // ALERTA CRÍTICO

        if (consumoDTO.getConsumoKwh() > 1000){

            Alerta alerta = new Alerta();

            alerta.setMensagem("ALERTA CRÍTICO: Consumo extremamente elevado.");
            alerta.setDataHoraAlerta(LocalDateTime.now());

            // FALTAVA ISSO
            alerta.setEquipamento(equipamento);

            alertaRepository.save(alerta);
        }

        equipamentoRepository.save(equipamento);

        return consumoEnergiaRepository.save(consumoEnergia);
    }

    public List<ConsumoEnergia> historicoEquipamento(Long idEquipamento){
        return consumoEnergiaRepository.findAll()
                .stream()
                .filter(c -> c.getEquipamento().getIdEquipamento().equals(idEquipamento))
                .toList();
    }
}