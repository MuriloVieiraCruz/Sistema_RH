package com.senai.sistema_rh_sa.service;

import com.senai.sistema_rh_sa.dto.Frete;
import com.senai.sistema_rh_sa.entity.DadosDoGrafico;
import com.senai.sistema_rh_sa.entity.Repasse;
import com.senai.sistema_rh_sa.service.exception.MetodoNaoSuportadoException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;
import java.util.Map;

public interface GraficoService {

    default public Map<Integer, DadosDoGrafico> calcularDadosPor(
            @Positive(message = "O ano deve ser positivo")
            @NotNull(message = "A ano é obrigatório")
            Integer ano,
            @Positive(message = "O mês deve ser positivo")
            Integer mes){
        throw new MetodoNaoSuportadoException("Este método não é suportado para para a operação atual");
    }

}
