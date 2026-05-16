package br.com.fiap.gestao.energetica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "EMPRESA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_EMPRESA"
    )
    @SequenceGenerator(
            name = "SEQ_EMPRESA",
            sequenceName = "SEQ_EMPRESA",
            allocationSize = 1
    )
    @Column(name = "id_empresa")
    private Long idEmpresa;

    @Column(name = "nm_empresa", nullable = false, length = 100)
    private String nomeEmpresa;

    @JsonIgnore
    @OneToMany(mappedBy = "empresa")
    private List<Setor> setores;
}
