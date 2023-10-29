package com.senai.sistema_rh_sa.dto;

import com.senai.sistema_rh_sa.model.Endereco;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
public class EntregadorDto {

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 200, min = 3, message = "O nome deve conter entre 200 e 3 caracteres")
    private String nome;

    @CPF(message = "O CPF está inválido")
    private String cpf;

    @Embedded
    @Valid
    private Endereco endereco;

    @NotBlank(message = "O número da habilitação é obrigatório")
    @Size(max = 10, min = 10, message = "O número da habilitação deve conter 10 caracteres")
    private String numeroHabilitacao;

    @NotBlank(message = "O telefone é obrigatório")
    @Size(max = 14, min = 14, message = "O telefone deve conter 14 caracteres")
    private String telefone;
}
