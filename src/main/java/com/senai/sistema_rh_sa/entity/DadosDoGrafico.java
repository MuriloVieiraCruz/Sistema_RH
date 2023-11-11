package com.senai.sistema_rh_sa.entity;

import jakarta.persistence.Entity;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity(name = "DadosDoGrafico")
public class DadosDoGrafico {


    private Integer ano;

    private Integer mes;

    private Integer semana;

    private BigDecimal volumeMovimentadoDeRepasses;
}
