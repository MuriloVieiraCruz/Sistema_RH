package com.senai.sistema_rh_sa.service;

import com.senai.sistema_rh_sa.entity.Entregador;
import com.senai.sistema_rh_sa.entity.enums.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Validated
public interface EntregadorService {

    public Entregador salvar(
            @Valid
            @NotNull(message = "O entregador é obrigatório")
            Entregador novoEntregador);

    public void alterarStatusPor(
            @Positive(message = "O ID deve ser maior que 0")
            @NotNull(message = "O ID é obrigatório")
            Integer id,
            @NotNull(message = "O status é obrigatório")
            Status status);

    public Entregador excluirPor(
            @Positive(message = "O ID deve ser maior que 0")
            @NotNull(message = "O ID é obrigatório")
            Integer id
    );

    public Entregador buscarPor(
            @Positive(message = "O ID deve ser maior que 0")
            @NotNull(message = "O ID é obrigatório")
            Integer id);

    public Integer buscarIdPor(
            @Email(message = "O formato do e-mail está inválido")
            @NotBlank(message = "O email é obrigatório")
            String email);

    public Page<Entregador> listarPor(
            @NotBlank(message = "Para a listagem é obrigatório informar o nome")
            String nome,
            Optional<String> cpf,
            Optional<String> email,
            Optional<String> numeroHabilitacao,
            Optional<String> telefone,
            Pageable paginacao);
}
