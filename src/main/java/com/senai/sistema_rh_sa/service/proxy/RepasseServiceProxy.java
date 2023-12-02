package com.senai.sistema_rh_sa.service.proxy;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.senai.sistema_rh_sa.dto.Frete;
import com.senai.sistema_rh_sa.entity.Repasse;
import com.senai.sistema_rh_sa.repository.RepasseRepository;
import com.senai.sistema_rh_sa.service.impl.JReportServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.senai.sistema_rh_sa.dto.Filtro;
import com.senai.sistema_rh_sa.service.RepasseService;

@Service
public class RepasseServiceProxy implements RepasseService {

    @Autowired
    @Qualifier("repasseServiceImpl")
    private RepasseService service;

    @Autowired
    private RepasseRepository repository;
    
    @Autowired
    private ProducerTemplate toApiFrete;

    @Override
    public List<Repasse> calcularRepassesPor(Integer ano, Integer mes) {
        Boolean isConsultaRepassesExistentes = repository.verificaBuscaPorDadosExistentesNoBanco(ano, mes);

        if (isConsultaRepassesExistentes) {
            List<Repasse> repasses = service.buscarRepassesExistentes(ano, mes);
            return repasses;
        }

        JSONObject requestBodyAnoMes = new JSONObject();
        requestBodyAnoMes.put("ano", ano);
        requestBodyAnoMes.put("mes", mes);
        JSONArray jsonArray = toApiFrete.requestBody("direct:buscarFrete", requestBodyAnoMes, JSONArray.class);
        List<Frete> listaDeFretes = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Frete frete = new Frete();
            frete.setId(jsonObject.getInt("id"));
            frete.setValorTotal(jsonObject.getBigDecimal("valorTotal"));
            frete.setDataMovimento((Instant) jsonObject.get("dataMovimento"));
            frete.setIdEntregador(jsonObject.getInt("idEntregador"));
            listaDeFretes.add(frete);
        }

        List<Repasse> repasses = this.service.calcularRepassesPor(listaDeFretes, ano, mes);
        return repasses;
    }


}
