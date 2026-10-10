package br.com.fiap.gestao.energetica.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "CONSUMO_ENERGIA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsumoEnergia {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_CONSUMO_ENERGIA"
    )
    @SequenceGenerator(
            name = "SEQ_CONSUMO_ENERGIA",
            sequenceName = "SEQ_CONSUMO",
            allocationSize = 1
    )
    @Column(name = "id_consumo")
    private Long idConsumo;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "consumo_kwh", nullable = false)
    private Double consumoKwh;

    @ManyToOne
    @JoinColumn(name = "EQUIPAMENTO_id_equipamento")
    private Equipamento equipamento;

}
