package br.com.fiap.gestao.energetica.service;

import br.com.fiap.gestao.energetica.dto.SetorDTO;
import br.com.fiap.gestao.energetica.model.Empresa;
import br.com.fiap.gestao.energetica.model.Setor;
import br.com.fiap.gestao.energetica.repository.EmpresaRepository;
import br.com.fiap.gestao.energetica.repository.SetorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SetorService {

    private final SetorRepository setorRepository;
    private final EmpresaRepository empresaRepository;

    public List<Setor> listarSetores() {
        return setorRepository.findAll();
    }

    public Setor salvarSetor(SetorDTO setorDTO) {

        Empresa empresa = empresaRepository.findById(setorDTO.getIdEmpresa())
                .orElseThrow(() ->
                        new RuntimeException("Empresa não encontrada!"));

        Setor setor = new Setor();

        setor.setNomeSetor(setorDTO.getNomeSetor());
        setor.setEmpresa(empresa);

        return setorRepository.save(setor);
    }

    public Setor atualizarSetor(Long id, SetorDTO setorDTO) {

        Setor setor = setorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Setor não encontrado!"));

        Empresa empresa = empresaRepository.findById(setorDTO.getIdEmpresa())
                .orElseThrow(() ->
                        new RuntimeException("Empresa não encontrada!"));

        setor.setNomeSetor(setorDTO.getNomeSetor());
        setor.setEmpresa(empresa);

        return setorRepository.save(setor);
    }

    public void deletarSetor(Long id) {

        Setor setor = setorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Setor não encontrado!"));

        setorRepository.delete(setor);
    }
}