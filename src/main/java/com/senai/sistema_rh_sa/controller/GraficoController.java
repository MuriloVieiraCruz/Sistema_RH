package com.senai.sistema_rh_sa.controller;

import com.senai.sistema_rh_sa.dto.DadosGrafico;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/grafico")
public class GraficoController {


    @Autowired
    private MapConverter converter;

    @Autowired
    @Qualifier("graficoServiceProxy")
    private GraficoService service;

    @PostMapping
    private ResponseEntity<?> buscarDadosPor(@RequestBody Filtro filtro) {
        List<DadosGrafico> dados = service.calcularDadosPor(filtro.getAno(), filtro.getMes());
        List<Map<String, Object>> listagem = new ArrayList<Map<String,Object>>();
        for (DadosGrafico dadoGrafico : dados) {
            listagem.add(converter(dadoGrafico));
        }
        return ResponseEntity.ok(listagem);
    }

    private Map<String, Object> converter(DadosGrafico dadosGrafico) {
        Map<String, Object> graficoMap = new HashMap<String, Object>();
        graficoMap.put("ano", dadosGrafico.getAno());

        Map<String, Object> mesMap = new HashMap<String, Object>();
        mesMap.put("nome", dadosGrafico.getMes().getNome());
        mesMap.put("volumeMovimentadoDeRepasses", dadosGrafico.getMes().getVolumeMovimentadoDeRepasses());

        graficoMap.put("mes", mesMap);
        return graficoMap;
    }
}
