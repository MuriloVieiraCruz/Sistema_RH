package com.senai.sistema_rh_sa.integracao.rota;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.senai.sistema_rh_sa.dto.RepasseDto;
import com.senai.sistema_rh_sa.integracao.processor.ErrorProcessor;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.camel.component.http.HttpMethods;

@Component
public class ToApiFrete extends RouteBuilder {

    @Value("${url.requisicao.frete.api}")
    private String urlDeRequisicao;

    @Autowired
    private ErrorProcessor errorProcessor;
    
    private List<RepasseDto> repasseDtoList;

    @Override
    public void configure() throws Exception {
        from("direct:enviarRequisicao")
                .doTry()
                    .setHeader(Exchange.HTTP_METHOD, HttpMethods.GET)
                    .setHeader(Exchange.CONTENT_TYPE, simple("application/json;charset=UTF-8"))
                    .to(urlDeRequisicao)
                    .process(new Processor() {
						
						@Override
						public void process(Exchange exchange) throws Exception {
							
							String responseBody = exchange.getIn().getBody(String.class);
							
							Gson gson = new Gson();
							Type freteTypeList = new TypeToken<List<RepasseDto>>(){}.getType();
							repasseDtoList = gson.fromJson(responseBody, freteTypeList);
						}
					})
        .doCatch(Exception.class)
        .setProperty("error", simple("${exception"))
        .process(errorProcessor)
        .end();
    }
    
    public List<RepasseDto> obterRepasseDtoList() {
        return repasseDtoList;
    }
}
