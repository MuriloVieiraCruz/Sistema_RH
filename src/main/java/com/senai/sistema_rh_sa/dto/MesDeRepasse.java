package com.senai.sistema_rh_sa.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MesDeRepasse {
	
	private String nome;
	
	private BigDecimal valorRepassado;

}
