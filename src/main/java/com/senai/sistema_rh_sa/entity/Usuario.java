package com.senai.sistema_rh_sa.entity;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Usuario {

    @Size(max = 50, message = "O login do usuário não deve conter mais do que 50 caracteres")
    @NotBlank(message = "O login do usuário é obrigatório")
    private String login;

    @NotBlank(message = "A senha do usuário é obrigatória")
    @Column(name = "senha")
    private String senha;
}
