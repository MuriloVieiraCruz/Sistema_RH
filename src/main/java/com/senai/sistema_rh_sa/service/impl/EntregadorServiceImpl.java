package com.senai.sistema_rh_sa.service.impl;

import com.google.common.base.Preconditions;
import com.senai.sistema_rh_sa.dto.Entregador;
import com.senai.sistema_rh_sa.entity.Endereco;
import com.senai.sistema_rh_sa.entity.enums.Status;
import com.senai.sistema_rh_sa.repository.EntregadorRepository;
import com.senai.sistema_rh_sa.service.EntregadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EntregadorServiceImpl implements EntregadorService {

    @Autowired
    private EntregadorRepository repository;

    @Override
    public com.senai.sistema_rh_sa.entity.Entregador salvar(Entregador entregadorDto) {
        com.senai.sistema_rh_sa.entity.Entregador entregador = new com.senai.sistema_rh_sa.entity.Entregador();
        entregador.setNome(entregadorDto.getNome());
        entregador.setCpf(entregadorDto.getCpf());
        Endereco endereco = new Endereco();
        endereco.setBairro(entregadorDto.getBairro());
        endereco.setLogradouro(entregadorDto.getLogradouro());
        endereco.setCidade(entregadorDto.getCidade());
        endereco.setEstado(entregadorDto.getEstado());
        entregador.setEndereco(endereco);
        entregador.setTelefone(entregadorDto.getTelefone());
        entregador.setNumeroHabilitacao(entregadorDto.getNumeroHabilitacao());
        entregador.setSeguroDeVida(entregadorDto.getSeguroDeVida());
        this.verificaCpf(entregador);
        this.verificaTelefone(entregador);
        this.verificaCNH(entregador);
        com.senai.sistema_rh_sa.entity.Entregador entregadorSalvo = this.repository.saveAndFlush(entregador);
        return entregadorSalvo;
    }

    @Override
    public void alterarStatusPor(Integer id, Status status) {
        Optional<com.senai.sistema_rh_sa.entity.Entregador> entregadorOpicional = repository.findById(id);
        Preconditions.checkArgument(entregadorOpicional.isPresent(),
                "Não foi encontrado entregador vinculado aos parâmetros informados");
        com.senai.sistema_rh_sa.entity.Entregador entragdorEncontrado = entregadorOpicional.get();
        Preconditions.checkArgument(entragdorEncontrado.getStatus() != status ,
                "O status não pode ser igual ao atual");
        this.repository.alterarStatusPor(id, status);
    }

    @Override
    public com.senai.sistema_rh_sa.entity.Entregador excluirPor(Integer id) {
        com.senai.sistema_rh_sa.entity.Entregador entregadorEncontrado = buscarPor(id);
        repository.deleteById(entregadorEncontrado.getId());
        return entregadorEncontrado;
    }

    @Override
    public com.senai.sistema_rh_sa.entity.Entregador buscarPor(Integer id) {
        Optional<com.senai.sistema_rh_sa.entity.Entregador> optionalGenre = repository.findById(id);
        Preconditions.checkArgument(optionalGenre.isPresent(),
                "Não foi encontrado entregador vinculado aos parâmetros informados");
        com.senai.sistema_rh_sa.entity.Entregador entregadorEncontrado = optionalGenre.get();
        Preconditions.checkArgument(entregadorEncontrado.isActive(),
                "O entregador encontrado está inativo");
        return entregadorEncontrado;
    }

    @Override
    public Page<com.senai.sistema_rh_sa.entity.Entregador> listarPor(String nome, Pageable paginacao) {
        return repository.listarPor(nome, paginacao);
    }

    private void verificaCpf(com.senai.sistema_rh_sa.entity.Entregador entregador) {
        com.senai.sistema_rh_sa.entity.Entregador entregadorEncontrado = repository.buscarPorCPF(entregador.getCpf());
        if (entregadorEncontrado != null) {
            if (entregador.isPersisted()) {
                Preconditions.checkArgument(entregadorEncontrado.equals(entregador),
                        "O CPF informado já existe");
            } else {
                throw new IllegalArgumentException("O CPF informado já existe");
            }
        }
    }

    private void verificaTelefone(com.senai.sistema_rh_sa.entity.Entregador entregador) {
        com.senai.sistema_rh_sa.entity.Entregador entregadorEncontrado = repository.buscarPorTelefone(entregador.getTelefone());
        if (entregadorEncontrado != null) {
            if (entregador.isPersisted()) {
                Preconditions.checkArgument(entregadorEncontrado.equals(entregador),
                        "O Telefone informado já existe");
            } else {
                throw new IllegalArgumentException("O Telefone informado já existe");
            }
        }
    }

    private void verificaCNH(com.senai.sistema_rh_sa.entity.Entregador entregador) {
        com.senai.sistema_rh_sa.entity.Entregador entregadorEncontrado = repository.buscarPorCNH(entregador.getNumeroHabilitacao());
        if (entregadorEncontrado != null) {
            if (entregador.isPersisted()) {
                Preconditions.checkArgument(entregadorEncontrado.equals(entregador),
                        "O número da habilitação informado já existe");
            } else {
                throw new IllegalArgumentException("O número da habilitação informado já existe");
            }
        }
    }
}
