package com.senai.sistema_rh_sa.service;

import java.util.List;

import com.senai.sistema_rh_sa.dto.AnoDeRepasse;
import com.senai.sistema_rh_sa.dto.DadosGrafico;
import com.senai.sistema_rh_sa.service.exception.MetodoNaoSuportadoException;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public interface GraficoService {

    default public AnoDeRepasse calcularDadosPor(
            @Positive(message = "O ano deve ser positivo")
            @NotNull(message = "A ano é obrigatório")
            Integer ano,
            Integer mes){
        throw new MetodoNaoSuportadoException("Este método não é suportado para para a operação atual");
    }

    public AnoDeRepasse calcularDadosPor(
            @Positive(message = "O ano deve ser positivo")
            @NotNull(message = "A ano é obrigatório")
            Integer ano);
}
