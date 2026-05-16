package br.com.fiap.gestao.energetica.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaDTO {


    @NotBlank(message = "Nome da empresa é obrigatório!")
    private String nomeEmpresa;
}
