package br.com.fiap.gestao.energetica.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsumoDTO {

    @NotNull(message = "Equipamento é obrigatório!")
    private Long idEquipamento;

    @NotNull(message = "Consumo obrigatório!")
    @PositiveOrZero(message = "Consumo não pode ser negativo")
    private Double consumoKwh;
}
