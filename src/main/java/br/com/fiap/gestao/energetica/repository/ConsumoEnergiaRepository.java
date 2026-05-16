package br.com.fiap.gestao.energetica.repository;

import br.com.fiap.gestao.energetica.model.ConsumoEnergia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumoEnergiaRepository extends JpaRepository<ConsumoEnergia,Long> {
}
