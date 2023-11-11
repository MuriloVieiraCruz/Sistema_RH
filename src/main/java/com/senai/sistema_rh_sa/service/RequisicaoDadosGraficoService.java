package com.senai.sistema_rh_sa.service;

import com.senai.sistema_rh_sa.dto.Frete;
import com.senai.sistema_rh_sa.service.exception.MetodoNaoSuportadoException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public interface RequisicaoDadosGraficoService {

    default public List<?> requisitarDadosPor(
            @Positive(message = "O ano deve ser positivo")
            @NotNull(message = "A ano é obrigatório")
            Integer ano,
            @Positive(message = "O mês deve ser positivo")
            Integer mes){
        throw new MetodoNaoSuportadoException("Este método não é suportado para para a operação atual");
    }

    default public List<?> requisitarDadosPor(List<Frete> entregas, Boolean ano, Boolean mes){
        throw new MetodoNaoSuportadoException("Este método não é suportado para para a operação atual");
    }

}
