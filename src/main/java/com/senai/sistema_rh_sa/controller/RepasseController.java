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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/repasses")
public class RepasseController {

    @Autowired
    @Qualifier("repasseServiceProxy")
    private RepasseService service;

    @Autowired
    private JReportServiceImpl jReportService;

    @PostMapping()
    private String gerarRelatorioPor(HttpServletResponse response, @RequestBody Filtro filtro) throws JRException, IOException {
        List<Repasse> repasses = service.calcularRepassesPor(filtro.getAno(), filtro.getMes());

        String relatorioPdf = jReportService.exportJasperReport(response, repasses);

        return relatorioPdf;
    }
}
