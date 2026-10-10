package br.com.fiap.gestao.energetica.service;

import br.com.fiap.gestao.energetica.dto.SetorDTO;
import br.com.fiap.gestao.energetica.model.Empresa;
import br.com.fiap.gestao.energetica.model.Setor;
import br.com.fiap.gestao.energetica.repository.EmpresaRepository;
import br.com.fiap.gestao.energetica.repository.SetorRepository;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SetorServiceTest {

    @Test
    void deveSalvarSetorAssociadoAEmpresa() {
        Empresa empresa = empresa(1L, "Empresa Verde");
        SetorService service = new SetorService(
                setorRepository(List.of(), Optional.empty()),
                empresaRepository(Optional.of(empresa))
        );

        Setor resultado = service.salvarSetor(new SetorDTO("TI", 1L));

        assertEquals("TI", resultado.getNomeSetor());
        assertEquals("Empresa Verde", resultado.getEmpresa().getNomeEmpresa());
    }

    @Test
    void deveAtualizarSetorExistente() {
        Empresa empresa = empresa(1L, "Empresa Verde");
        Setor setor = new Setor();
        setor.setIdSetor(10L);
        setor.setNomeSetor("Administrativo");

        SetorService service = new SetorService(
                setorRepository(List.of(setor), Optional.of(setor)),
                empresaRepository(Optional.of(empresa))
        );

        Setor resultado = service.atualizarSetor(10L, new SetorDTO("Operacoes", 1L));

        assertEquals("Operacoes", resultado.getNomeSetor());
        assertEquals(empresa, resultado.getEmpresa());
    }

    @Test
    void deveLancarErroQuandoEmpresaNaoExistirAoSalvarSetor() {
        SetorService service = new SetorService(
                setorRepository(List.of(), Optional.empty()),
                empresaRepository(Optional.empty())
        );

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.salvarSetor(new SetorDTO("TI", 99L))
        );

        assertEquals("Empresa não encontrada!", exception.getMessage());
    }

    private Empresa empresa(Long id, String nome) {
        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(id);
        empresa.setNomeEmpresa(nome);
        return empresa;
    }

    private SetorRepository setorRepository(List<Setor> setores, Optional<Setor> setorPorId) {
        return (SetorRepository) Proxy.newProxyInstance(
                SetorRepository.class.getClassLoader(),
                new Class<?>[]{SetorRepository.class},
                (proxy, method, args) -> switch (method.getName()) {
                    case "findAll" -> setores;
                    case "findById" -> setorPorId;
                    case "save" -> args[0];
                    case "delete" -> null;
                    default -> throw new UnsupportedOperationException(method.getName());
                }
        );
    }

    private EmpresaRepository empresaRepository(Optional<Empresa> empresaPorId) {
        return (EmpresaRepository) Proxy.newProxyInstance(
                EmpresaRepository.class.getClassLoader(),
                new Class<?>[]{EmpresaRepository.class},
                (proxy, method, args) -> switch (method.getName()) {
                    case "findById" -> empresaPorId;
                    default -> throw new UnsupportedOperationException(method.getName());
                }
        );
    }
}
