package com.senai.sistema_rh_sa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.senai.sistema_rh_sa.entity.enums.Papel;
import com.senai.sistema_rh_sa.entity.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.br.CPF;

@Data
@Entity(name = "Entregador")
@Table(name = "entregadores")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Entregador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Integer id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 200, min = 3, message = "O nome deve conter entre 200 e 3 caracteres")
    @Column(name = "nome")
    private String nome;

    @CPF(message = "O CPF está inválido")
    @Column(name = "cpf")
    @EqualsAndHashCode.Include
    private String cpf;

    @Embedded
    @Valid
    private Endereco endereco;

    @NotBlank(message = "O número da habilitação é obrigatório")
    @Size(max = 9, min = 9, message = "O número da habilitação deve conter 10 caracteres")
    @Column(name = "numero_habilitacao")
    @EqualsAndHashCode.Include
    private String numeroHabilitacao;

    @NotBlank(message = "O telefone é obrigatório")
    @Size(max = 14, min = 14, message = "O telefone deve conter 14 caracteres")
    @Column(name = "telefone")
    @EqualsAndHashCode.Include
    private String telefone;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O papel do entregador é obrigatório")
    @Column(name = "papel")
    private Papel papel;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O status do entregador é obrigatório")
    @Column(name = "status")
    private Status status;

    @NotNull(message = "O seguro de vida é obrigatório")
    @Column(name = "seguro_de_vida")
    private Boolean seguroDeVida;

    public Entregador() {
        this.papel = Papel.ENTREGADOR;
        this.status = Status.A;
    }

    @JsonIgnore
    @Transient
    public boolean isPersisted() {
        return getId() != null && getId() > 0;
    }

    @JsonIgnore
    @Transient
    public boolean isActive() {
        return getStatus() == Status.A;
    }
}
