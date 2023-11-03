package com.senai.sistema_rh_sa.service.proxy;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.senai.sistema_rh_sa.dto.Filtro;
import com.senai.sistema_rh_sa.dto.RepasseDto;
import com.senai.sistema_rh_sa.service.RepasseService;

@Service
public class RepassesServiceProxy implements RepasseService {

    @Autowired
    @Qualifier("entregasServiceImpl")
    private RepasseService repasseService;
    
    @Autowired
    private ProducerTemplate toApiFrete;

    @Override
    public CompletableFuture<List<RepasseDto>> buscarRepasses(Integer mes, Integer ano) {
    	Filtro filtro = montarFiltroPor(mes, ano);
    	
    	CompletableFuture<List<RepasseDto>> future = new CompletableFuture<>();
    	this.toApiFrete.asyncRequestBody("direct:enviarRequisicao", filtro, List.class)
    	.whenComplete((result, exception) -> {
            if (exception != null) {
                future.completeExceptionally(exception);
            } else {
                future.complete(result);
            }
        });     
        return future;
    }
    
    private Filtro montarFiltroPor(Integer mes, Integer ano) {
    	Filtro filtro = new Filtro();
    	filtro.setAno(ano);
    	filtro.setMes(mes);
    	return filtro;
    }

}
