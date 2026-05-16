package br.com.fiap.gestao.energetica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SetorDTO {

    @NotBlank(message = "Nome do setor é obrigatório!")
    private String nomeSetor;

    @NotNull(message = "Empresa é obrigatória!")
    private Long idEmpresa;
}
