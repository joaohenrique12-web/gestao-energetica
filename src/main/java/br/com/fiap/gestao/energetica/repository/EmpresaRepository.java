package br.com.fiap.gestao.energetica.repository;

import br.com.fiap.gestao.energetica.model.ConsumoEnergia;
import br.com.fiap.gestao.energetica.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa,Long> {
}
