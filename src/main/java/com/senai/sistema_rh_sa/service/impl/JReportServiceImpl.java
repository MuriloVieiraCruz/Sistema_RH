package com.senai.sistema_rh_sa.service.impl;

import com.senai.sistema_rh_sa.entity.Repasse;
import com.senai.sistema_rh_sa.repository.RepasseRepository;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JReportServiceImpl {

    @Autowired
    private RepasseRepository repasseRepository;

    public void exportJasperReport(HttpServletResponse response, List<Repasse> repasses) throws IOException, JRException {

        File file = ResourceUtils.getFile("com/senai/sistema_rh_sa/report/pagamentosRH.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(repasses);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("createBy", "RH_system");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }
}
