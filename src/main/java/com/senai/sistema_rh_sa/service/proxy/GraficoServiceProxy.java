package com.senai.sistema_rh_sa.service.proxy;

import com.senai.sistema_rh_sa.dto.DadosGrafico;
import com.senai.sistema_rh_sa.entity.DadosDoGrafico;
import com.senai.sistema_rh_sa.entity.Repasse;
import com.senai.sistema_rh_sa.service.GraficoService;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GraficoServiceProxy implements GraficoService {

    @Autowired
    @Qualifier("graficoServiceImpl")
    private GraficoService service;

    @Override
    public List<DadosGrafico> calcularDadosPor(Integer ano, Integer mes) {
       return service.calcularDadosPor(ano, mes);
    }
}
