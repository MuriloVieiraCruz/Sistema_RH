package com.senai.sistema_rh_sa.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class Filtro {

	@Range(max = 2, message = "O formato do mês está incorreto")
	@Positive(message = "O mês deve ser positivo")
	private Integer mes;

	@Range(max = 4, min = 4, message = "O formato do ano está incorreto")
	@Positive(message = "O ano deve ser positivo")
	@NotNull(message = "O ano é obrigatório")
	private Integer ano;

}
