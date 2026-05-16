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
}
