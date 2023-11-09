package com.senai.sistema_rh_sa.service.proxy;

import java.util.ArrayList;
import java.util.List;

import com.senai.sistema_rh_sa.dto.Frete;
import com.senai.sistema_rh_sa.entity.Repasse;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.senai.sistema_rh_sa.dto.Filtro;
import com.senai.sistema_rh_sa.service.RepasseService;

@Service
public class RepasseServiceProxy implements RepasseService {

    @Autowired
    @Qualifier("repasseServiceImpl")
    private RepasseService repasseService;
    
    @Autowired
    private ProducerTemplate toApiFrete;

    @Override
    public List<Repasse> calcularRepassesPor(Integer mes, Integer ano) {
        //TODO Verificar a implementação nessa parte de código
    	Filtro filtro = montarFiltroPor(mes, ano);
        List<Frete> freteList = (List<Frete>) this.toApiFrete.sendBody("direct:enviarRequisicao", null, filtro);
        List<Repasse> repasseList = this.repasseService.calcularRepassesPor(freteList);
        return repasseList;
    }
    
    private Filtro montarFiltroPor(Integer mes, Integer ano) {
    	Filtro filtro = new Filtro();
    	filtro.setAno(ano);
    	filtro.setMes(mes);
    	return filtro;
    }

}
