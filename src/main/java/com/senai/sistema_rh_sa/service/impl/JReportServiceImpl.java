package com.senai.sistema_rh_sa.service.impl;

import com.senai.sistema_rh_sa.entity.Repasse;
import com.senai.sistema_rh_sa.repository.RepasseRepository;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class JReportServiceImpl {

    @Autowired
    private RepasseRepository repasseRepository;

    public String exportJasperReport(HttpServletResponse response, List<Repasse> repasses) throws IOException, JRException {

        response.setContentType("application/pdf");
        DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        String dataHoraAtual = formatador.format(new Date());

        String chaveCabecalho = "Content-Disposition";
        String valorCabecalho = "attachment; filename=pdf" + dataHoraAtual + ".pdf";
        response.setHeader(chaveCabecalho, valorCabecalho);

        File file = ResourceUtils.getFile("classpath:pagamentosRH.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(repasses);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("createBy", "RH_system");

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
        JRExporter exportador = new JRPdfExporter();
        exportador.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exportador.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
        exportador.exportReport();

        byte[] output = outputStream.toByteArray();
        String pdfBase64 = Base64.getEncoder().encodeToString(output);

        return pdfBase64;

        //JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }
}
