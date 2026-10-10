package br.com.fiap.gestao.energetica.service;

import br.com.fiap.gestao.energetica.model.Alerta;
import br.com.fiap.gestao.energetica.repository.AlertaRepository;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlertaServiceTest {

    @Test
    void deveListarSomenteCincoAlertasMaisRecentes() {
        List<Alerta> alertas = List.of(
                alerta("A1", LocalDateTime.now().minusHours(6)),
                alerta("A2", LocalDateTime.now().minusHours(5)),
                alerta("A3", LocalDateTime.now().minusHours(4)),
                alerta("A4", LocalDateTime.now().minusHours(3)),
                alerta("A5", LocalDateTime.now().minusHours(2)),
                alerta("A6", LocalDateTime.now().minusHours(1))
        );
        AlertaService service = new AlertaService(alertaRepository(alertas));

        List<Alerta> resultado = service.listarAlertasRecentes();

        assertEquals(5, resultado.size());
        assertEquals("A6", resultado.get(0).getMensagem());
        assertEquals("A2", resultado.get(4).getMensagem());
    }

    private Alerta alerta(String mensagem, LocalDateTime dataHora) {
        Alerta alerta = new Alerta();
        alerta.setMensagem(mensagem);
        alerta.setDataHoraAlerta(dataHora);
        return alerta;
    }

    private AlertaRepository alertaRepository(List<Alerta> alertas) {
        return (AlertaRepository) Proxy.newProxyInstance(
                AlertaRepository.class.getClassLoader(),
                new Class<?>[]{AlertaRepository.class},
                (proxy, method, args) -> switch (method.getName()) {
                    case "findAll" -> alertas;
                    default -> throw new UnsupportedOperationException(method.getName());
                }
        );
    }
}
