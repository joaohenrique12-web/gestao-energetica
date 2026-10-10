package br.com.fiap.gestao.energetica.service;

import br.com.fiap.gestao.energetica.dto.ConsumoDTO;
import br.com.fiap.gestao.energetica.model.Alerta;
import br.com.fiap.gestao.energetica.model.ConsumoEnergia;
import br.com.fiap.gestao.energetica.model.Equipamento;
import br.com.fiap.gestao.energetica.repository.AlertaRepository;
import br.com.fiap.gestao.energetica.repository.ConsumoEnergiaRepository;
import br.com.fiap.gestao.energetica.repository.EquipamentoRepository;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConsumoServiceTest {

    @Test
    void deveRegistrarConsumoLigandoEquipamentoQuandoConsumoForMaiorQueZero() {
        Equipamento equipamento = equipamento(1L, "DESLIGADO");
        List<Alerta> alertasSalvos = new ArrayList<>();
        ConsumoService service = service(equipamento, List.of(), alertasSalvos);

        ConsumoEnergia resultado = service.registrarConsumo(new ConsumoDTO(1L, 300.0));

        assertEquals(300.0, resultado.getConsumoKwh());
        assertEquals("LIGADO", equipamento.getStatus());
        assertEquals(0, alertasSalvos.size());
    }

    @Test
    void deveRegistrarAlertaQuandoConsumoForZeroEDesligarEquipamento() {
        Equipamento equipamento = equipamento(1L, "LIGADO");
        List<Alerta> alertasSalvos = new ArrayList<>();
        ConsumoService service = service(equipamento, List.of(), alertasSalvos);

        service.registrarConsumo(new ConsumoDTO(1L, 0.0));

        assertEquals("DESLIGADO", equipamento.getStatus());
        assertEquals(1, alertasSalvos.size());
        assertEquals("Equipamento desligado.", alertasSalvos.get(0).getMensagem());
    }

    @Test
    void deveRegistrarAlertasDeAltoConsumoECritico() {
        Equipamento equipamento = equipamento(1L, "LIGADO");
        List<Alerta> alertasSalvos = new ArrayList<>();
        ConsumoService service = service(equipamento, List.of(), alertasSalvos);

        service.registrarConsumo(new ConsumoDTO(1L, 1200.0));

        assertEquals(2, alertasSalvos.size());
        assertEquals("ALERTA: Alto consumo de energia detectado.", alertasSalvos.get(0).getMensagem());
        assertEquals("ALERTA CRÍTICO: Consumo extremamente elevado.", alertasSalvos.get(1).getMensagem());
    }

    @Test
    void deveFiltrarHistoricoPorEquipamento() {
        Equipamento equipamento1 = equipamento(1L, "LIGADO");
        Equipamento equipamento2 = equipamento(2L, "LIGADO");

        ConsumoEnergia consumo1 = consumo(equipamento1, 100.0);
        ConsumoEnergia consumo2 = consumo(equipamento2, 200.0);
        ConsumoEnergia consumo3 = consumo(equipamento1, 300.0);

        ConsumoService service = service(equipamento1, List.of(consumo1, consumo2, consumo3), new ArrayList<>());

        List<ConsumoEnergia> resultado = service.historicoEquipamento(1L);

        assertEquals(2, resultado.size());
    }

    @Test
    void deveLancarErroQuandoEquipamentoNaoExistirAoRegistrarConsumo() {
        ConsumoService service = service(null, List.of(), new ArrayList<>());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.registrarConsumo(new ConsumoDTO(99L, 100.0))
        );

        assertEquals("Equipamento não encontrado!", exception.getMessage());
    }

    private ConsumoService service(
            Equipamento equipamento,
            List<ConsumoEnergia> consumos,
            List<Alerta> alertasSalvos
    ) {
        return new ConsumoService(
                consumoRepository(consumos),
                equipamentoRepository(Optional.ofNullable(equipamento)),
                alertaRepository(alertasSalvos)
        );
    }

    private Equipamento equipamento(Long id, String status) {
        Equipamento equipamento = new Equipamento();
        equipamento.setIdEquipamento(id);
        equipamento.setStatus(status);
        equipamento.setNomeEquipamento("Equipamento " + id);
        return equipamento;
    }

    private ConsumoEnergia consumo(Equipamento equipamento, Double consumoKwh) {
        ConsumoEnergia consumo = new ConsumoEnergia();
        consumo.setEquipamento(equipamento);
        consumo.setConsumoKwh(consumoKwh);
        return consumo;
    }

    private ConsumoEnergiaRepository consumoRepository(List<ConsumoEnergia> consumos) {
        return (ConsumoEnergiaRepository) Proxy.newProxyInstance(
                ConsumoEnergiaRepository.class.getClassLoader(),
                new Class<?>[]{ConsumoEnergiaRepository.class},
                (proxy, method, args) -> switch (method.getName()) {
                    case "findAll" -> consumos;
                    case "save" -> args[0];
                    default -> throw new UnsupportedOperationException(method.getName());
                }
        );
    }

    private EquipamentoRepository equipamentoRepository(Optional<Equipamento> equipamentoPorId) {
        return (EquipamentoRepository) Proxy.newProxyInstance(
                EquipamentoRepository.class.getClassLoader(),
                new Class<?>[]{EquipamentoRepository.class},
                (proxy, method, args) -> switch (method.getName()) {
                    case "findById" -> equipamentoPorId;
                    case "save" -> args[0];
                    default -> throw new UnsupportedOperationException(method.getName());
                }
        );
    }

    private AlertaRepository alertaRepository(List<Alerta> alertasSalvos) {
        return (AlertaRepository) Proxy.newProxyInstance(
                AlertaRepository.class.getClassLoader(),
                new Class<?>[]{AlertaRepository.class},
                (proxy, method, args) -> switch (method.getName()) {
                    case "save" -> {
                        alertasSalvos.add((Alerta) args[0]);
                        yield args[0];
                    }
                    default -> throw new UnsupportedOperationException(method.getName());
                }
        );
    }
}
