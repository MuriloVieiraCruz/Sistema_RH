package com.senai.sistema_rh_sa.service;

import com.senai.sistema_rh_sa.dto.NovoEntregador;
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

@Validated
public interface EntregadorService {

    public Entregador salvar(
            @Valid
            @NotNull(message = "O entregador é obrigatório")
            NovoEntregador novoEntregador);

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

    public Page<Entregador> listarPor(String nome, String cpf, String cnh, String telefone, Pageable paginacao);
}
