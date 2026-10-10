package br.com.fiap.gestao.energetica.service;

import br.com.fiap.gestao.energetica.dto.EmpresaDTO;
import br.com.fiap.gestao.energetica.model.Empresa;
import br.com.fiap.gestao.energetica.repository.EmpresaRepository;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmpresaServiceTest {

    @Test
    void deveSalvarEmpresaComNomeInformadoNoDto() {
        EmpresaService service = new EmpresaService(repository(Optional.empty()));

        Empresa resultado = service.salvarEmpresa(new EmpresaDTO("Empresa Verde"));

        assertEquals("Empresa Verde", resultado.getNomeEmpresa());
    }

    @Test
    void deveAtualizarEmpresaExistente() {
        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(1L);
        empresa.setNomeEmpresa("Nome antigo");

        EmpresaService service = new EmpresaService(repository(Optional.of(empresa)));

        Empresa resultado = service.atualizarEmpresa(1L, new EmpresaDTO("Nome novo"));

        assertEquals("Nome novo", resultado.getNomeEmpresa());
    }

    @Test
    void deveLancarErroAoAtualizarEmpresaInexistente() {
        EmpresaService service = new EmpresaService(repository(Optional.empty()));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.atualizarEmpresa(99L, new EmpresaDTO("Nome novo"))
        );

        assertEquals("Empresa não encontrada", exception.getMessage());
    }

    private EmpresaRepository repository(Optional<Empresa> empresaPorId) {
        return (EmpresaRepository) Proxy.newProxyInstance(
                EmpresaRepository.class.getClassLoader(),
                new Class<?>[]{EmpresaRepository.class},
                (proxy, method, args) -> switch (method.getName()) {
                    case "findById" -> empresaPorId;
                    case "save" -> args[0];
                    default -> throw new UnsupportedOperationException(method.getName());
                }
        );
    }
}
