package com.senai.sistema_rh_sa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.Instant;

@Entity(name = "Repasses")
@Table(name = "repasses")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Repasses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    @NotNull(message = "A data de movimentação nõ pode ser nula")
    @Column(name = "data_movimentacao")
    private Instant dataMoviementacao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    @NotNull(message = "A data de pagamento não pode ser nula")
    @Column(name = "data_movimentacao")
    private Instant dataPagamento;

    @DecimalMin(value = "0.0", inclusive = true, message = "O valor precisa ser positivo")
    @Digits(integer = 8, fraction = 2, message = "O valor precisa conter o formato 'NNNNNNNNN.NN'")
    @NotNull(message = "O valor não pode ser nulo")
    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "ano")
    private Integer ano;

    @Column(name = "ano")
    private Integer mes;

    @DecimalMin(value = "0.0", inclusive = true, message = "A bonificacao precisa ser positivo")
    @Digits(integer = 8, fraction = 2, message = "A bonificacao precisa conter o formato 'NNNNNNNNN.NN'")
    @NotNull(message = "A bonificacao não pode ser nula")
    @Column(name = "bonificacao")
    private BigDecimal bonificacao;

    @Column(name = "quantidade_entregas")
    private Integer quantidadeDeEntregas;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "O entregador é obrigatório")
    @JoinColumn(name = "entregador_id")
    private Entregador entregador;
}
