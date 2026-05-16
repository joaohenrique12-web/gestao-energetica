package br.com.fiap.gestao.energetica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "SETOR")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Setor {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_SETOR"
    )
    @SequenceGenerator(
            name = "SEQ_SETOR",
            sequenceName = "SEQ_SETOR",
            allocationSize = 1
    )
    private Long idSetor;

    @Column(name = "nm_setor", nullable = false, length = 100)
    private String nomeSetor;

    @ManyToOne
    @JoinColumn(name = "EMPRESA_id_empresa")
    private Empresa empresa;

    @JsonIgnore
    @OneToMany(mappedBy = "setor")
    private List<Equipamento> equipamentos;
}
