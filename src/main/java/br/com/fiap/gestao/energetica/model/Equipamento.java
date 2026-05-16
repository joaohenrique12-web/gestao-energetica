package br.com.fiap.gestao.energetica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "EQUIPAMENTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Equipamento {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_EQUIPAMENTO"
    )
    @SequenceGenerator(
            name = "SEQ_EQUIPAMENTO",
            sequenceName = "SEQ_EQUIPAMENTO",
            allocationSize = 1
    )
    @Column(name = "id_equipamento")
    private Long idEquipamento;

    @Column(name = "nm_equipamento", nullable = false, length = 100)
    private String nomeEquipamento;

    @Column(nullable = false, length = 10)
    private String status;

    @Column(name = "consumo_watts", nullable = false)
    private Double consumoWatts;

    @ManyToOne
    @JoinColumn(name = "SETOR_id_setor")
    private Setor setor;

    @JsonIgnore
    @OneToMany(mappedBy = "equipamento")
    private List<ConsumoEnergia> consumos;

    @JsonIgnore
    @OneToMany(mappedBy = "equipamento")
    private List<Alerta> alertas;


}
