package com.senai.sistema_rh_sa.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.validation.annotation.Validated;

import com.senai.sistema_rh_sa.dto.RepasseDto;
import com.senai.sistema_rh_sa.service.exception.MetodoNaoSuportadoException;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
public interface RepasseService {

    default public CompletableFuture<List<RepasseDto>> buscarRepasses(
            Integer mes,
            @Positive(message = "O ano deve ser positivo")
            @NotNull(message = "A ano é obrigatório")
            Integer ano) {
        throw new MetodoNaoSuportadoException("Este método não é suportado para para a operação atual");
    }

    default public void calculaRepasse(
            @NotNull(message = "Para calcular o repasse é obrigatório passar uma lista válida")
            List<RepasseDto> repasseDtoList) {
        throw new MetodoNaoSuportadoException("Este método não é suportado para para a operação atual");
    }
}
