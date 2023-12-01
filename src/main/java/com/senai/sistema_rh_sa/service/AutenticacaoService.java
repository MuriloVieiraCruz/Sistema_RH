package com.senai.sistema_rh_sa.service;

import com.senai.sistema_rh_sa.dto.Frete;
import com.senai.sistema_rh_sa.entity.Repasse;
import com.senai.sistema_rh_sa.service.exception.MetodoNaoSuportadoException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface AutenticacaoService {

    public String autenticarUsuario(
            @NotBlank(message = "O login é obrigatório")
            String login,
            @NotBlank(message = "A senha é obrigatória")
            String senha);
}
