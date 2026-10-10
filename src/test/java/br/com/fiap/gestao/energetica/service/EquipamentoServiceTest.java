package br.com.fiap.gestao.energetica.service;

import br.com.fiap.gestao.energetica.model.Equipamento;
import br.com.fiap.gestao.energetica.repository.EquipamentoRepository;
import br.com.fiap.gestao.energetica.repository.SetorRepository;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EquipamentoServiceTest {

    @Test
    void deveListarApenasEquipamentosComAltoConsumo() {
        Equipamento servidor = equipamento("Servidor", "LIGADO", 900.0);
        Equipamento notebook = equipamento("Notebook", "LIGADO", 120.0);

        EquipamentoService service = new EquipamentoService(
                repository(List.of(servidor, notebook), Optional.empty()),
                setorRepository()
        );

        List<Equipamento> resultado = service.listarAltoConsumo();

        assertEquals(1, resultado.size());
        assertEquals("Servidor", resultado.get(0).getNomeEquipamento());
    }

    @Test
    void deveListarApenasEquipamentosLigados() {
        Equipamento ligado = equipamento("Servidor", "LIGADO", 500.0);
        Equipamento desligado = equipamento("Ar condicionado", "DESLIGADO", 800.0);

        EquipamentoService service = new EquipamentoService(
                repository(List.of(ligado, desligado), Optional.empty()),
                setorRepository()
        );

        List<Equipamento> resultado = service.listarEquipamentosLigados();

        assertEquals(1, resultado.size());
        assertEquals("Servidor", resultado.get(0).getNomeEquipamento());
    }

    @Test
    void deveDesligarEquipamentoExistente() {
        Equipamento equipamento = equipamento("Servidor", "LIGADO", 500.0);
        equipamento.setIdEquipamento(1L);

        EquipamentoService service = new EquipamentoService(
                repository(List.of(equipamento), Optional.of(equipamento)),
                setorRepository()
        );

        Equipamento resultado = service.desligarEquipamento(1L);

        assertEquals("DESLIGADO", resultado.getStatus());
    }

    @Test
    void deveLancarErroQuandoEquipamentoNaoExistir() {
        EquipamentoService service = new EquipamentoService(
                repository(List.of(), Optional.empty()),
                setorRepository()
        );

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.buscarPorId(99L)
        );

        assertEquals("Equipamento não encontrado!", exception.getMessage());
    }

    private Equipamento equipamento(String nome, String status, Double consumoWatts) {
        Equipamento equipamento = new Equipamento();
        equipamento.setNomeEquipamento(nome);
        equipamento.setStatus(status);
        equipamento.setConsumoWatts(consumoWatts);
        return equipamento;
    }

    private EquipamentoRepository repository(List<Equipamento> equipamentos, Optional<Equipamento> equipamentoPorId) {
        return (EquipamentoRepository) Proxy.newProxyInstance(
                EquipamentoRepository.class.getClassLoader(),
                new Class<?>[]{EquipamentoRepository.class},
                (proxy, method, args) -> switch (method.getName()) {
                    case "findAll" -> equipamentos;
                    case "findById" -> equipamentoPorId;
                    case "save" -> args[0];
                    default -> throw new UnsupportedOperationException(method.getName());
                }
        );
    }

    private SetorRepository setorRepository() {
        return (SetorRepository) Proxy.newProxyInstance(
                SetorRepository.class.getClassLoader(),
                new Class<?>[]{SetorRepository.class},
                (proxy, method, args) -> {
                    throw new UnsupportedOperationException(method.getName());
                }
        );
    }
}
