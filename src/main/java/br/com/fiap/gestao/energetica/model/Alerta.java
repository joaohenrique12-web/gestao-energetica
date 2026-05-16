package br.com.fiap.gestao.energetica.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ALERTA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Alerta {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_ALERTA"
    )
    @SequenceGenerator(
            name = "SEQ_ALERTA",
            sequenceName = "SEQ_ALERTA",
            allocationSize = 1
    )
    @Column(name = "id_alerta")
    private Long idAlerta;

    @Column(nullable = false, length = 255)
    private String mensagem;

    @Column(name = "data_hora_alerta", nullable = false)
    private LocalDateTime dataHoraAlerta;

    @ManyToOne
    @JoinColumn(name = "EQUIPAMENTO_id_equipamento")
    private Equipamento equipamento;
}
