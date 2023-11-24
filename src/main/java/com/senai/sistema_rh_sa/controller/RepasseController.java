package com.senai.sistema_rh_sa.controller;

import com.senai.sistema_rh_sa.dto.Filtro;
import com.senai.sistema_rh_sa.service.RepasseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/repasses")
public class RepasseController {

    @Autowired
    @Qualifier("repasseServiceProxy")
    private RepasseService service;

    @PostMapping()
    private ResponseEntity<?> gerarRelatorioPor(@RequestBody Filtro filtro) {
        service.calcularRepassesPor(filtro.getAno(), filtro.getMes());
        return null;
        //TODO implementar o retorno do relatório aqui
    }
}
