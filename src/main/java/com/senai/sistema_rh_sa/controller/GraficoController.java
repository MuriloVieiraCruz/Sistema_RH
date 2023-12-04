package com.senai.sistema_rh_sa.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.senai.sistema_rh_sa.dto.Filtro;
import com.senai.sistema_rh_sa.service.GraficoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senai.sistema_rh_sa.dto.AnoDeRepasse;
import com.senai.sistema_rh_sa.dto.MesDeRepasse;

@RestController
@RequestMapping("/grafico")
public class GraficoController {

    @Autowired
    private MapConverter converter;

    @Autowired
    @Qualifier("graficoServiceProxy")
    private GraficoService service;

    @GetMapping("/ano/{ano}")
    private ResponseEntity<?> gerarGraficoPor(
    		@PathVariable("ano")
    		Integer ano){

        AnoDeRepasse repasseAnual = service.calcularDadosPor(ano, null);
    	return ResponseEntity.ok(converter.toJsonMap(repasseAnual));
    }

    @PostMapping
    private ResponseEntity<?> buscarDadosPor(@RequestBody Filtro filtro) {
        AnoDeRepasse dados = service.calcularDadosPor(filtro.getAno(), filtro.getMes());
        List<Map<String, Object>> listagem = new ArrayList<Map<String,Object>>();
        return ResponseEntity.ok(listagem);
    }
}
