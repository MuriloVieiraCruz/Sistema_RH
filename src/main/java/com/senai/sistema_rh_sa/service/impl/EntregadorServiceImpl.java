package com.senai.sistema_rh_sa.service.impl;

import com.google.common.base.Preconditions;
import com.senai.sistema_rh_sa.entity.Entregador;
import com.senai.sistema_rh_sa.entity.enums.Status;
import com.senai.sistema_rh_sa.repository.EntregadorRepository;
import com.senai.sistema_rh_sa.repository.RepasseRepository;
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

    @Autowired
    private RepasseRepository repasseRepository;

    @Override
    public Entregador salvar(Entregador entregador) {
        this.verificaDados(entregador);
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
        Integer qtdeDeRepassesVinculados = repasseRepository.contarRepassesVinculadosPor(id);
        Preconditions.checkArgument(qtdeDeRepassesVinculados.equals(0),
                "O entregador está vinculado a um repasse e não pode ser excluído");
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
    public Entregador buscarPor(String email) {
        Entregador entregadorEncontrado = repository.buscarPor(email);
        Preconditions.checkNotNull(entregadorEncontrado,
                "Não foi encontrado entregador com o e-mail vinculado");
        return entregadorEncontrado;
    }

    @Override
    public Page<Entregador> listarPor(
            String nome,
            Optional<String> cpf,
            Optional<String> email,
            Optional<String> numeroHabilitacao,
            Optional<String> telefone,
            Pageable paginacao) {

        return repository.listarPor("%" + nome + "%", cpf, email, numeroHabilitacao, telefone, paginacao);
    }

    private void verificaDados(Entregador entregador) {
        Entregador entregadorEncontrado = repository.buscarPorCPF(entregador.getCpf());
        verificaIgualdade(entregador, entregadorEncontrado, "O CPF informado já existe");

        entregadorEncontrado = repository.buscarPorNumeroHabilitacao(entregador.getNumeroHabilitacao());
        verificaIgualdade(entregador, entregadorEncontrado, "O número da Habilitação informada já existe");

        entregadorEncontrado = repository.buscarPorTelefone(entregador.getTelefone());
        verificaIgualdade(entregador, entregadorEncontrado, "O telefone informado já existe");
    }

    private void verificaIgualdade(Entregador entregador, Entregador entregadorEncontrado, String mensagemErro) {
        if (entregadorEncontrado != null) {
            if (entregador.isPersisted()) {
                Preconditions.checkArgument(entregadorEncontrado.equals(entregador),
                        mensagemErro);
            } else {
                throw new IllegalArgumentException(mensagemErro);
            }
        }
    }
}
