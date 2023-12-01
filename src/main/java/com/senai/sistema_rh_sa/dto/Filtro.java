package com.senai.sistema_rh_sa.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class Filtro {

	private Integer mes;

	//@Max(value = 4, message = "O formato do ano não pode conter mais de 4 digitos")
	//@Min(value = 4, message = "O formato do ano não pode conter menos de 4 digitos")
	@Positive(message = "O ano deve ser positivo")
	@NotNull(message = "O ano é obrigatório")
	private Integer ano;

}
