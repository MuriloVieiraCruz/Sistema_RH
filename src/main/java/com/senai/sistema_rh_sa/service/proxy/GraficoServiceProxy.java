package com.senai.sistema_rh_sa.service.proxy;

import com.senai.sistema_rh_sa.dto.AnoDeRepasse;
import com.senai.sistema_rh_sa.service.GraficoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class GraficoServiceProxy implements GraficoService {

    @Autowired
    @Qualifier("graficoServiceImpl")
    private GraficoService service;

    @Override
    public AnoDeRepasse calcularDadosPor(Integer ano, Integer mes) {
       return service.calcularDadosPor(ano, mes);
    }

    @Override
    public AnoDeRepasse calcularDadosPor(Integer ano) {
        return service.calcularDadosPor(ano);
    }
}
