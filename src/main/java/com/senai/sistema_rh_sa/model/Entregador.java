package com.senai.sistema_rh_sa.model;

import com.senai.sistema_rh_sa.model.enums.Papel;
import com.senai.sistema_rh_sa.model.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
@Entity(name = "Entregador")
@Table(name = "entregadores")
public class Entregador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 200, min = 3, message = "O nome deve conter entre 200 e 3 caracteres")
    @Column(name = "nome")
    private String nome;

    @CPF(message = "O CPF está inválido")
    @Column(name = "cpf")
    private String cpf;

    @Embedded
    @Valid
    private Endereco endereco;

    @NotBlank(message = "O número da habilitação é obrigatório")
    @Size(max = 10, min = 10, message = "O número da habilitação deve conter 10 caracteres")
    @Column(name = "numero_habilitacao")
    private String numeroHabilitacao;

    @NotBlank(message = "O telefone é obrigatório")
    @Size(max = 14, min = 14, message = "O telefone deve conter 14 caracteres")
    @Column(name = "")
    private String telefone;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "O papel do entregador é obrigatório")
    @Column(name = "papel")
    private Papel papel;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "O status do entregador é obrigatório")
    @Column(name = "status")
    private Status status;

    public Entregador() {
        this.papel = Papel.ENTREGADOR;
        this.status = Status.ATIVO;
    }
}
