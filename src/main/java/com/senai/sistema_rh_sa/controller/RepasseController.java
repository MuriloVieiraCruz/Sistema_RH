package com.senai.sistema_rh_sa.controller;

import com.senai.sistema_rh_sa.dto.Filtro;
import com.senai.sistema_rh_sa.entity.Repasse;
import com.senai.sistema_rh_sa.service.RepasseService;
import com.senai.sistema_rh_sa.service.impl.JReportServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/repasses")
public class RepasseController {

    @Autowired
    @Qualifier("repasseServiceProxy")
    private RepasseService service;

    @Autowired
    private JReportServiceImpl jReportService;

    @PostMapping
    private ResponseEntity<?> gerarRelatorioPor(HttpServletResponse response, @RequestBody Filtro filtro) throws JRException, IOException {
        List<Repasse> repasses = service.calcularRepassesPor(filtro.getAno(), filtro.getMes());
        String relatorioPdf = jReportService.exportJasperReport(response, repasses);
        Map<String, Object> retorno = new HashMap<String, Object>();
        retorno.put("relatorio", relatorioPdf);
        return ResponseEntity.ok(retorno);
    }
}
