package com.senai.sistema_rh_sa.service.proxy;

import com.senai.sistema_rh_sa.entity.Entregador;
import com.senai.sistema_rh_sa.entity.enums.Status;
import com.senai.sistema_rh_sa.service.EntregadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EntregadorServiceProxy implements EntregadorService {

    @Autowired
    @Qualifier("entregadorServiceImpl")
    private EntregadorService service;

    @Override
    public Entregador salvar(Entregador entregador) {
        return this.service.salvar(entregador);
    }

    @Override
    public void alterarStatusPor(Integer id, Status status) {
        this.service.alterarStatusPor(id, status);
    }

    @Override
    public Entregador excluirPor(Integer id) {
        return this.service.excluirPor(id);
    }

    @Override
    public Entregador buscarPor(Integer id) {
        return this.service.buscarPor(id);
    }

    @Override
    public Integer buscarIdPor(String email) {
        return service.buscarIdPor(email);
    }

    @Override
    public Page<Entregador> listarPor(
            String nome,
            Optional<String> cpf,
            Optional<String> email,
            Optional<String> numeroHabilitacao,
            Optional<String> telefone,
            Pageable paginacao) {
        return this.service.listarPor(nome, cpf, email, numeroHabilitacao, telefone, paginacao);
    }

}
