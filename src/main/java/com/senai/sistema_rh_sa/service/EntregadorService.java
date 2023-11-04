package com.senai.sistema_rh_sa.service;

import com.senai.sistema_rh_sa.dto.Entregador;
import com.senai.sistema_rh_sa.entity.enums.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

@Validated
public interface EntregadorService {

    public com.senai.sistema_rh_sa.entity.Entregador salvar(
            @Valid
            @NotNull(message = "O entregador é obrigatório")
            Entregador entregador);

    public void alterarStatusPor(
            @Positive(message = "O ID deve ser maior que 0")
            @NotNull(message = "O ID é obrigatório")
            Integer id,
            @NotNull(message = "O status é obrigatório")
            Status status);

    public com.senai.sistema_rh_sa.entity.Entregador excluirPor(
            @Positive(message = "O ID deve ser maior que 0")
            @NotNull(message = "O ID é obrigatório")
            Integer id
    );

    public com.senai.sistema_rh_sa.entity.Entregador buscarPor(
            @Positive(message = "O ID deve ser maior que 0")
            @NotNull(message = "O ID é obrigatório")
            Integer id);

    public Page<com.senai.sistema_rh_sa.entity.Entregador> listarPor(String nome, Pageable paginacao);
}
