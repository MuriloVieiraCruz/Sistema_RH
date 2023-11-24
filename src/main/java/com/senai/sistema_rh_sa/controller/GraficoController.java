package com.senai.sistema_rh_sa.controller;

import com.senai.sistema_rh_sa.dto.Filtro;
import com.senai.sistema_rh_sa.entity.DadosDoGrafico;
import com.senai.sistema_rh_sa.service.GraficoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/grafico")
public class GraficoController {

    @Autowired
    @Qualifier("graficoServiceProxy")
    private GraficoService service;

    @PostMapping
    private ResponseEntity<?> buscarDadosPor(@RequestBody Filtro filtro) {
        Map<Integer, DadosDoGrafico> dados = service.calcularDadosPor(filtro.getAno(), filtro.getMes());
        return ResponseEntity.ok(dados);
    }
}
