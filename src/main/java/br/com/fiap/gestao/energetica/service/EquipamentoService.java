package br.com.fiap.gestao.energetica.service;

import br.com.fiap.gestao.energetica.dto.EquipamentoDTO;
import br.com.fiap.gestao.energetica.model.Equipamento;
import br.com.fiap.gestao.energetica.model.Setor;
import br.com.fiap.gestao.energetica.repository.EquipamentoRepository;
import br.com.fiap.gestao.energetica.repository.SetorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;
    private final SetorRepository setorRepository;

    public List<Equipamento> listarEquipamentos(){
        return equipamentoRepository.findAll();
    }

    public Equipamento buscarPorId(Long id){
        return equipamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado!"));
    }

    public Equipamento salvarEquipamento(EquipamentoDTO equipamentoDTO){
        Setor setor = setorRepository.findById(equipamentoDTO.getIdSetor())
                .orElseThrow(() -> new RuntimeException("Setor não encontradoo!"));

        Equipamento equipamento = new Equipamento();
        equipamento.setNomeEquipamento(equipamentoDTO.getNomeEquipamento());
        equipamento.setStatus(equipamentoDTO.getStatus());
        equipamento.setConsumoWatts(equipamentoDTO.getConsumoWatts());
        equipamento.setSetor(setor);

        return equipamentoRepository.save(equipamento);
    }

    public Equipamento atualizarEquipamento(Long id, EquipamentoDTO equipamentoDTO){
        Equipamento equipamento = buscarPorId(id);
        equipamento.setNomeEquipamento(equipamentoDTO.getNomeEquipamento());
        equipamento.setStatus(equipamentoDTO.getStatus());
        equipamento.setConsumoWatts(equipamentoDTO.getConsumoWatts());
        return equipamentoRepository.save(equipamento);
    }

    public void deletarEquipamento( Long id){
        equipamentoRepository.deleteById(id);
    }

    public List<Equipamento> listarEquipamentosLigados(){
        return equipamentoRepository.findAll()
                .stream()
                .filter(e -> e.getStatus().equalsIgnoreCase("LIGADO"))
                .toList();
    }

    public List<Equipamento> listarAltoConsumo(){
        return equipamentoRepository.findAll()
                .stream()
                .filter(e -> e.getConsumoWatts() > 700)
                .toList();
    }

    public Equipamento desligarEquipamento(Long id){
        Equipamento equipamento = buscarPorId(id);
        equipamento.setStatus("DESLIGADO");

        return equipamentoRepository.save(equipamento);
    }
}
