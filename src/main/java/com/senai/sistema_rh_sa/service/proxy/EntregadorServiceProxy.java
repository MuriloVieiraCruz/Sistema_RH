package com.senai.sistema_rh_sa.service.proxy;

import com.senai.sistema_rh_sa.dto.NovoEntregador;
import com.senai.sistema_rh_sa.entity.Entregador;
import com.senai.sistema_rh_sa.entity.enums.Status;
import com.senai.sistema_rh_sa.service.EntregadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EntregadorServiceProxy implements EntregadorService {

    @Autowired
    @Qualifier("entregadorServiceImpl")
    private EntregadorService service;

    @Override
    public Entregador salvar(NovoEntregador novoEntregador) {
        return this.service.salvar(novoEntregador);
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
    public Page<Entregador> listarPor(String nome, Pageable paginacao) {
        return this.service.listarPor(nome, paginacao);
    }

}
