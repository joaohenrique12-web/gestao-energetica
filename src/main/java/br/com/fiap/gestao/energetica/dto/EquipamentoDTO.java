package br.com.fiap.gestao.energetica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EquipamentoDTO {

    @NotBlank(message = "Nome do equipamento é obrigatório!")
    private String nomeEquipamento;

    @NotBlank(message = "Status do equipamento é obrigatório!")
    private String status;

    @NotNull(message = "Consumo do equipamento é obrigatório!")
    @Positive(message = "Consumo deve ser positivo!")
    private Double consumoWatts;

    @NotNull(message = "Setor é obrigatório!")
    private Long idSetor;
}
