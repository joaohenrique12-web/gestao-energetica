package br.com.fiap.gestao.energetica.repository;

import br.com.fiap.gestao.energetica.model.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
}
