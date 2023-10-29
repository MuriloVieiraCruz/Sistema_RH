package com.senai.sistema_rh_sa.service.impl;

import com.google.common.base.Preconditions;
import com.senai.sistema_rh_sa.dto.EntregadorDto;
import com.senai.sistema_rh_sa.dto.EntregadorSalvoDto;
import com.senai.sistema_rh_sa.model.Entregador;
import com.senai.sistema_rh_sa.model.enums.Status;
import com.senai.sistema_rh_sa.repository.EntregadorRepository;
import com.senai.sistema_rh_sa.service.EntregadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EntregadorServiceImpl implements EntregadorService {

    @Autowired
    private EntregadorRepository repository;

    @Override
    public Entregador salvar(EntregadorDto entregadorDto) {
        this.verificaCpf(entregadorDto.getCpf());
        Entregador entregador = new Entregador();
        entregador.setNome(entregador.getNome());
        entregador.setCpf(entregador.getCpf());
        entregador.setEndereco(entregador.getEndereco());
        entregador.setTelefone(entregador.getTelefone());
        Entregador entregadorSalvo = this.repository.saveAndFlush(entregador);
        return entregadorSalvo;
    }

    @Override
    public Entregador alterar(EntregadorSalvoDto entregadorSalvoDto) {
        this.verificaCpf(entregadorSalvoDto.getCpf());
        Entregador entregador = new Entregador();
        entregador.setNome(entregador.getNome());
        entregador.setCpf(entregador.getCpf());
        entregador.setEndereco(entregador.getEndereco());
        entregador.setTelefone(entregador.getTelefone());
        Entregador entregadorSalvo = this.repository.saveAndFlush(entregador);
        return entregadorSalvo;
    }

    @Override
    public void alterarStatusPor(Integer id, Status status) {
        Optional<Entregador> entregadorOpicional = repository.findById(id);
        Preconditions.checkArgument(entregadorOpicional.isPresent(),
                "Não foi encontrado entregador vinculado aos parâmetros informados");
        Entregador entragdorEncontrado = entregadorOpicional.get();
        Preconditions.checkArgument(entragdorEncontrado.getStatus() != status ,
                "O status é igual ao anterior");
        this.repository.alterarStatusPor(id, status);
    }

    @Override
    public Entregador excluirPor(Integer id) {
        return null;
    }

    @Override
    public Entregador buscarPor(Integer id) {
        return null;
    }

    @Override
    public Page<Entregador> listarPor(String nome, Page paginacao) {
        return null;
    }

    private void verificaCpf(String cpfInformado) {
        Integer qtdDeCpfEncontrados = repository.contarPor(cpfInformado);
        Preconditions.checkArgument(qtdDeCpfEncontrados >= 1,
                "Esse cpf já existe");
    }
}
