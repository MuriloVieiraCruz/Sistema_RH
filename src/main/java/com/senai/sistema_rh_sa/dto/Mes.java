package com.senai.sistema_rh_sa.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Mes {

    private String nome;

    private BigDecimal volumeMovimentadoDeRepasses;
}
