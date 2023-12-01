package com.senai.sistema_rh_sa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DadosDoGrafico {

    private Integer ano;

    private Integer mes;

    private BigDecimal volumeMovimentadoDeRepasses;
}
