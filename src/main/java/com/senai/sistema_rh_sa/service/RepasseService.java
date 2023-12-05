package com.senai.sistema_rh_sa.service;

import com.senai.sistema_rh_sa.dto.Frete;
import com.senai.sistema_rh_sa.entity.Repasse;
import com.senai.sistema_rh_sa.service.exception.MetodoNaoSuportadoException;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface RepasseService {

    default public List<Repasse> calcularRepassesPor(
            //@Max(value = 4, message = "O formato do ano não pode conter mais de 4 digitos")
            //@Min(value = 4, message = "O formato do ano não pode conter menos de 4 digitos")
            @Positive(message = "O ano deve ser positivo")
            @NotNull(message = "A ano é obrigatório")
            Integer ano,

            @Max(value = 12, message = "O formato do mês não pode conter mais de 12 digitos")
            @Min(value = 1, message = "O formato do mês não pode conter menos de 1 digito")
            @Positive(message = "O mês deve ser positivo")
            Integer mes){
        throw new MetodoNaoSuportadoException("Este método não é suportado para para a operação atual");
    }

    default public List<Repasse> calcularRepassesPor(
            @NotNull(message = "A lista de entregas é obrigatório")
            List<Frete> entregas,

            @Positive(message = "O ano deve ser positivo")
            @NotNull(message = "A ano é obrigatório")
            Integer ano,

            @Range(min = 1, max = 12, message = "O mês deve estar entre 1 e 12")
            @NotNull(message = "O mês é obrigatório")
            Integer mes){
        throw new MetodoNaoSuportadoException("Este método não é suportado para para a operação atual");
    }

    default public List<Repasse> buscarRepassesExistentes(
            @Positive(message = "O ano deve ser positivo")
            @NotNull(message = "A ano é obrigatório")
            Integer ano,

            @Range(min = 1, max = 12, message = "O mês deve estar entre 1 e 12")
            @NotNull(message = "A mês é obrigatório")
            Integer mes) {
        throw new MetodoNaoSuportadoException("Este método não é suportado para para a operação atual");
    }
}
