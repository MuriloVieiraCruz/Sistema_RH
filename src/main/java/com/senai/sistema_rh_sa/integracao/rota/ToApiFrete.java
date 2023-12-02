package com.senai.sistema_rh_sa.integracao.rota;

import com.senai.sistema_rh_sa.integracao.processor.ErrorProcessor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.camel.component.http.HttpMethods;

import java.io.Serializable;

@Component
public class ToApiFrete extends RouteBuilder implements Serializable {

    @Value("${url.requisicao.logistica}")
    private String urlDeRequisicao;
    @Value("${url.requisicao.token.logistica}")
    private String urlToken;

    @Autowired
    private ErrorProcessor errorProcessor;

    @Override
    public void configure() throws Exception {
        from("direct:buscarFrete")
                .doTry()
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String responseJson = exchange.getIn().getBody(String.class);
                        JSONObject jsonObject = new JSONObject(responseJson);
                        Integer ano = jsonObject.getInt("ano");
                        Integer mes = jsonObject.getInt("mes");
                        exchange.setProperty("ano", ano);
                        exchange.setProperty("mes", mes);
                    }
                })
                .to("direct:autenticacaoLogistica")
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json;charset=UTF-8"))
                .setHeader("Authorization", simple("Bearer ${exchangeProperty.token}"))
                .toD(urlDeRequisicao + "/frete/ano/${exchangeProperty.ano}/mes/${exchangeProperty.mes}")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String jsonBody = exchange.getMessage().getBody(String.class);
                        JSONArray jsonArray = new JSONArray(jsonBody);
                        exchange.getMessage().setBody(jsonArray.toString());
                    }
                })
                .doCatch(Exception.class)
                .setProperty("error", simple("${exception}"))
                .process(errorProcessor)
                .end();
    }
}
