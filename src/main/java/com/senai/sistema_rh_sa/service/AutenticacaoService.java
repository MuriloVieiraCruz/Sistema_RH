package com.senai.sistema_rh_sa.service;

import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public interface AutenticacaoService {

    public String autenticarUsuario(
            @NotBlank(message = "O login é obrigatório")
            String login,
            @NotBlank(message = "A senha é obrigatória")
            String senha);
}
