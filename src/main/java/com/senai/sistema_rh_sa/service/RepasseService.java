package com.senai.sistema_rh_sa.service;

import com.senai.sistema_rh_sa.dto.Frete;
import com.senai.sistema_rh_sa.entity.Repasse;
import com.senai.sistema_rh_sa.service.exception.MetodoNaoSuportadoException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface RepasseService {

    default public List<Repasse> calcularRepassesPor(
            @Positive(message = "O ano deve ser positivo")
            @NotNull(message = "A ano é obrigatório")
            Integer ano,
            @Positive(message = "O mês deve ser positivo")
            @NotNull(message = "A mês é obrigatório")
            Integer mes){
        throw new MetodoNaoSuportadoException("Este método não é suportado para para a operação atual");
    }

    default public List<Repasse> calcularRepassesPor(List<Frete> entregas){
        throw new MetodoNaoSuportadoException("Este método não é suportado para para a operação atual");
    }
}
