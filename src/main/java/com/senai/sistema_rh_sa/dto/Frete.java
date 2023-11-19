package com.senai.sistema_rh_sa.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class Frete {

    @Positive(message = "O ID deve ser maior que 0")
    @NotNull(message = "O ID é obrigatório")
    private Integer id;

    @DecimalMin(value = "0.0", inclusive = true, message = "O frete precisa ser positivo")
    @Digits(integer = 3, fraction = 2, message = "O frete precisa conter o formato 'NNN.NN'")
    @NotNull(message = "O frete não pode ser nulo")
    private BigDecimal valorTotal;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    @NotNull(message = "A data de movimento não pode ser nula")
    private Instant dataMovimento;

    @NotNull(message = "O entregador não pode ser nulo")
    private Integer idEntregador;
}
