package com.senai.sistema_rh_sa.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.senai.sistema_rh_sa.entity.Endereco;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
public class Entregador {

    private Integer id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 200, min = 3, message = "O nome deve conter entre 200 e 3 caracteres")
    private String nome;

    @CPF(message = "O CPF está inválido")
    private String cpf;

    @Size(max = 200, message = "O logradouro da cidade não deve conatar com mais de 200 caracteres")
    @NotBlank(message = "O logradouro é obrigatória")
    private String logradouro;

    @Size(max = 50, message = "O bairro da cidade não deve conatar com mais de 50 caracteres")
    @NotBlank(message = "O bairro é obrigatória")
    private String bairro;

    @Size(max = 80, message = "O nome da cidade não deve conatar com mais de 80 caracteres")
    @NotBlank(message = "A cidade é obrigatória")
    private String cidade;

    @Size(max = 80, message = "O nome do estado não deve conatar com mais de 80 caracteres")
    @NotBlank(message = "O estado é obrigatório")
    private String estado;

    @NotBlank(message = "O número da habilitação é obrigatório")
    @Size(max = 9, min = 9, message = "O número da habilitação deve conter 10 caracteres")
    private String numeroHabilitacao;

    @NotBlank(message = "O telefone é obrigatório")
    @Size(max = 14, min = 14, message = "O telefone deve conter 14 caracteres")
    private String telefone;

    @NotNull(message = "O seguro de vida é obrigatório")
    private Boolean seguroDeVida;

    @JsonIgnore
    @Transient
    public boolean isPersisted() {
        return getId() != null && getId() > 0;
    }
}
