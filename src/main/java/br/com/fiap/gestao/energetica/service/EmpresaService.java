package br.com.fiap.gestao.energetica.service;

import br.com.fiap.gestao.energetica.dto.EmpresaDTO;
import br.com.fiap.gestao.energetica.model.Empresa;
import br.com.fiap.gestao.energetica.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public List<Empresa> listarEmpresas() {
        return empresaRepository.findAll();
    }

    public Empresa salvarEmpresa(EmpresaDTO empresaDTO) {

        Empresa empresa = new Empresa();

        empresa.setNomeEmpresa(empresaDTO.getNomeEmpresa());

        return empresaRepository.save(empresa);
    }

    public Empresa atualizarEmpresa(Long id, EmpresaDTO empresaDTO) {

        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Empresa não encontrada"));

        empresa.setNomeEmpresa(empresaDTO.getNomeEmpresa());

        return empresaRepository.save(empresa);
    }

    public void deletarEmpresa(Long id) {

        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Empresa não encontrada"));

        empresaRepository.delete(empresa);
    }
}