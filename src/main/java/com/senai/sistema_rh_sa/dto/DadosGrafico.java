package com.senai.sistema_rh_sa.dto;

import jakarta.persistence.Embedded;
import lombok.Data;

@Data
public class DadosGrafico {

    private Integer ano;

    @Embedded
    private Mes mes;
}
