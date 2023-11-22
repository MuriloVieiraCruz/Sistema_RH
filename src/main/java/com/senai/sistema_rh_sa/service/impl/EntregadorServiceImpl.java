package com.senai.sistema_rh_sa.service.impl;

import com.google.common.base.Preconditions;
import com.senai.sistema_rh_sa.dto.NovoEntregador;
import com.senai.sistema_rh_sa.entity.Endereco;
import com.senai.sistema_rh_sa.entity.Entregador;
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
    public Entregador salvar(NovoEntregador novoEntregadorDto) {
        Entregador entregador = new Entregador();
        entregador.setNome(novoEntregadorDto.getNome());
        entregador.setCpf(novoEntregadorDto.getCpf());
        Endereco endereco = new Endereco();
        endereco.setBairro(novoEntregadorDto.getBairro());
        endereco.setLogradouro(novoEntregadorDto.getLogradouro());
        endereco.setCidade(novoEntregadorDto.getCidade());
        endereco.setEstado(novoEntregadorDto.getEstado());
        entregador.setEndereco(endereco);
        entregador.setTelefone(novoEntregadorDto.getTelefone());
        entregador.setNumeroHabilitacao(novoEntregadorDto.getNumeroHabilitacao());
        entregador.setSeguroDeVida(novoEntregadorDto.getSeguroDeVida());
        this.verificaCpf(entregador);
        this.verificaTelefone(entregador);
        this.verificaCNH(entregador);
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
                "O status não pode ser igual ao atual");
        this.repository.alterarStatusPor(id, status);
    }

    @Override
    public Entregador excluirPor(Integer id) {
        Entregador entregadorEncontrado = buscarPor(id);
        repository.deleteById(entregadorEncontrado.getId());
        return entregadorEncontrado;
    }

    @Override
    public Entregador buscarPor(Integer id) {
        Optional<Entregador> optionalGenre = repository.findById(id);
        Preconditions.checkArgument(optionalGenre.isPresent(),
                "Não foi encontrado entregador vinculado aos parâmetros informados");
        Entregador entregadorEncontrado = optionalGenre.get();
        Preconditions.checkArgument(entregadorEncontrado.isActive(),
                "O entregador encontrado está inativo");
        return entregadorEncontrado;
    }

    @Override
    public Page<Entregador> listarPor(String nome, Pageable paginacao) {
        return repository.listarPor(nome, paginacao);
    }

    private void verificaCpf(Entregador entregador) {
        Entregador entregadorEncontrado = repository.buscarPorCPF(entregador.getCpf());
        if (entregadorEncontrado != null) {
            if (entregador.isPersisted()) {
                Preconditions.checkArgument(entregadorEncontrado.equals(entregador),
                        "O CPF informado já existe");
            } else {
                throw new IllegalArgumentException("O CPF informado já existe");
            }
        }
    }

    private void verificaTelefone(Entregador entregador) {
        Entregador entregadorEncontrado = repository.buscarPorTelefone(entregador.getTelefone());
        if (entregadorEncontrado != null) {
            if (entregador.isPersisted()) {
                Preconditions.checkArgument(entregadorEncontrado.equals(entregador),
                        "O Telefone informado já existe");
            } else {
                throw new IllegalArgumentException("O Telefone informado já existe");
            }
        }
    }

    private void verificaCNH(Entregador entregador) {
        Entregador entregadorEncontrado = repository.buscarPorCNH(entregador.getNumeroHabilitacao());
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
