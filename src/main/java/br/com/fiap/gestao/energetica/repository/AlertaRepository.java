package br.com.fiap.gestao.energetica.repository;

import br.com.fiap.gestao.energetica.model.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {
}
