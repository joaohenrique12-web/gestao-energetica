package br.com.fiap.gestao.energetica.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlertaDTO {


    private String mensagem;

    private LocalDateTime dataHoraAlerta;

    private Long equipamento;
}
