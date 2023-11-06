package com.senai.sistema_rh_sa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Entity(name = "Repasse")
@Table(name = "repasses")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Repasse {

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

    @DecimalMin(value = "0.0", inclusive = true, message = "O valor bruto precisa ser positivo")
    @Digits(integer = 6, fraction = 2, message = "O valor bruto precisa conter o formato 'NNNNNN.NN'")
    @NotNull(message = "O valor bruto não pode ser nulo")
    @Column(name = "valor_bruto")
    private BigDecimal valorBruto;

    @DecimalMin(value = "0.0", inclusive = true, message = "O valor líquido precisa ser positivo")
    @Digits(integer = 6, fraction = 2, message = "O valor líquido precisa conter o formato 'NNNNNNNNN.NN'")
    @NotNull(message = "O valor líquido não pode ser nulo")
    @Column(name = "valor_liquido")
    private BigDecimal valorLiquido;

    @Positive(message = "O ano deve ser positivo")
    @NotNull(message = "O ano é obrigatório")
    @Column(name = "ano")
    private Integer ano;

    @Positive(message = "O mês deve ser positivo")
    @NotNull(message = "O mês é obrigatório")
    @Column(name = "ano")
    private Integer mes;

    @DecimalMin(value = "0.0", inclusive = true, message = "A bonificacao precisa ser positivo")
    @Digits(integer = 8, fraction = 2, message = "A bonificacao precisa conter o formato 'NNNNNNNNN.NN'")
    @NotNull(message = "A bonificacao não pode ser nula")
    @Column(name = "bonificacao")
    private BigDecimal bonificacao;

    @Column(name = "quantidade_entregas")
    private Integer quantidadeDeEntregas;

    @DecimalMin(value = "0.0", inclusive = true, message = "A taxa de seguro precisa ser positivo")
    @Digits(integer = 8, fraction = 2, message = "A taxa de seguro precisa conter o formato 'NNNNNNNNN.NN'")
    @NotNull(message = "A taxa de seguro não pode ser nula")
    @Column(name = "taxa_seguro_de_vida")
    private BigDecimal taxaSeguroDeVida;

    @DecimalMin(value = "0.0", inclusive = false, message = "O percentual de seguro não pode ser inferior a 0.01%")
    @DecimalMax(value = "100.0", inclusive = false, message = "O percentual de seguro não pode ser superior a 100.00%")
    @Digits(integer = 2, fraction = 2, message = "O percentual de seguro deve possuir o formato 'NN.NN'")
    @Column(name = "percentual_seguro_de_vida")
    private BigDecimal percentualSeguroDeVida;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "O entregador é obrigatório")
    @JoinColumn(name = "entregador_id")
    private Entregador entregador;

    public Repasse() {
        this.percentualSeguroDeVida = new BigDecimal(7);
        this.dataMoviementacao = Instant.now();
        this.dataPagamento = Instant.now();
    }
}
