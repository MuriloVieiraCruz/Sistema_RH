package com.senai.sistema_rh_sa.controller;

import com.google.common.base.Preconditions;
import com.senai.sistema_rh_sa.entity.Entregador;
import com.senai.sistema_rh_sa.entity.enums.Status;
import com.senai.sistema_rh_sa.service.EntregadorService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/entregador")
public class EntregadorController {

    @Autowired
    private MapConverter converter;

    @Autowired
    @Qualifier("entregadorServiceProxy")
    private EntregadorService service;

    @PostMapping
    @Transactional
    public ResponseEntity<?> salvar(@RequestBody Entregador entregador) {
        Preconditions.checkArgument(!entregador.isPersisted(),
                "O entregador não pode conter ID na inserção");
        Entregador entregadorSalvo = service.salvar(entregador);
        return ResponseEntity.created(URI.create("/entregador/id/" + entregadorSalvo.getId())).build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> alterar(@RequestBody Entregador entregador) {
        Preconditions.checkArgument(entregador.isPersisted(),
                "O entregador precisa ter um ID para alteração");
        Entregador entregadorAlterado = service.salvar(entregador);
        return ResponseEntity.ok(converter.toJsonMap(entregadorAlterado));
    }

    @PatchMapping("/id/{id}/status/{status}")
    @Transactional
    public ResponseEntity<?> atualizarStatusPor(@PathVariable("id") Integer id, @PathVariable("status") Status status) {
        this.service.alterarStatusPor(id, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> buscarPor(@PathVariable("id") Integer id) {
        Entregador entregadorEncontrado = service.buscarPor(id);
        return ResponseEntity.ok(converter.toJsonMap(entregadorEncontrado));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> buscarPor(@PathVariable("email") String email) {
        Integer idEntregador = service.buscarIdPor(email);
        return ResponseEntity.ok(converter.toJsonMap(idEntregador));
    }

    @GetMapping
    public ResponseEntity<?> listarPor(
            @RequestParam("nome") String nome,
            @RequestParam("cpf") Optional<String> cpf,
            @RequestParam("email") Optional<String> email,
            @RequestParam("numeroHabilitacao") Optional<String> numeroHabilitacao,
            @RequestParam("telefone") Optional<String> telefone,
            @RequestParam("pagina") Optional<Integer> pagina) {
        Pageable paginacao = null;
        if (pagina.isPresent()) {
            paginacao = PageRequest.of(pagina.get(), 20);
        } else {
            paginacao = PageRequest.of(0, 20);
        }

        Page<Entregador> entregadores = service.listarPor(nome, cpf, email, numeroHabilitacao, telefone, paginacao);
        return ResponseEntity.ok(converter.toJsonList(entregadores));
    }

    @DeleteMapping("id/{id}")
    @Transactional
    public ResponseEntity<?> excluirPor(@PathVariable("id") Integer id) {
        Entregador entregadorExcluido = service.excluirPor(id);
        return ResponseEntity.ok(converter.toJsonMap(entregadorExcluido));
    }
}
