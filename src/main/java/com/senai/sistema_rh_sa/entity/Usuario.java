package com.senai.sistema_rh_sa.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Usuario {

    @Size(max = 100, message = "O login do usuário não deve conter mais do que 100 caracteres")
    @NotBlank(message = "O login do usuário é obrigatório")
    private String login;

    @NotBlank(message = "A senha do usuário é obrigatória")
    private String senha;
}
