/*
package com.senai.sistema_rh_sa.reports;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Controller
public class ReportController {

    @Autowired
    private JasperReport jasperReport;

    @RequestMapping("/gerarRelatorio")
    public void gerarRelatorio(HttpServletResponse response) throws JRException, IOException {

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, new JRBeanCollectionDataSource(dados));

        // Exportar para PDF e enviar como resposta
        response.setContentType("application/x-pdf");
        response.setHeader("Content-disposition", "inline; filename=relatorio.pdf");

        OutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }
}
*/
