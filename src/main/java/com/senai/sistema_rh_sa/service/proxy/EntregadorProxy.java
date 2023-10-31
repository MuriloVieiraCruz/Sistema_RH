package com.senai.sistema_rh_sa.service.proxy;

import com.senai.sistema_rh_sa.dto.EntregadorDto;
import com.senai.sistema_rh_sa.model.Entregador;
import com.senai.sistema_rh_sa.model.enums.Status;
import com.senai.sistema_rh_sa.service.EntregadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class EntregadorProxy implements EntregadorService {

    @Autowired
    @Qualifier("entregadorServiceImpl")
    private EntregadorService service;

    @Override
    public Entregador salvar(EntregadorDto entregadorDto) {
        return this.service.salvar(entregadorDto);
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
    public Page<Entregador> listarPor(String nome, Page paginacao) {
        return this.service.listarPor(nome, paginacao);
    }
}
