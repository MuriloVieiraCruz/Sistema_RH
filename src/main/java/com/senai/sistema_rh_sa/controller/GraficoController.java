package com.senai.sistema_rh_sa.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.senai.sistema_rh_sa.dto.DadosGrafico;
import com.senai.sistema_rh_sa.dto.Filtro;
import com.senai.sistema_rh_sa.dto.MesDeRepasse;
import com.senai.sistema_rh_sa.service.GraficoService;

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
    	
    	AnoDeRepasse repasseAnual = new AnoDeRepasse();
    	repasseAnual.setAno(2023);
    	
    	MesDeRepasse mes = new MesDeRepasse();    	
    	mes.setNome("Julho");
    	mes.setValorRepassado(new BigDecimal(10500.2));
    	repasseAnual.getMeses().add(mes);
    	
    	mes = new MesDeRepasse();
    	mes.setNome("Agosto");
    	mes.setValorRepassado(new BigDecimal(4500.2));
    	repasseAnual.getMeses().add(mes);
    	
    	mes = new MesDeRepasse();
    	mes.setNome("Setembro");
    	mes.setValorRepassado(new BigDecimal(9200.2));
    	repasseAnual.getMeses().add(mes);
    	
    	mes = new MesDeRepasse();
    	mes.setNome("Outubro");
    	mes.setValorRepassado(new BigDecimal(11000.2));
    	repasseAnual.getMeses().add(mes);
    	
    	return ResponseEntity.ok(converter.toJsonMap(repasseAnual));    	    
    }

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
