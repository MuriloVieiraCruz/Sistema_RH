/*
package com.senai.sistema_rh_sa.integracao.rota;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpMethods;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ToApiAutenticacao extends RouteBuilder {

    @Value("${url.requisicao.autenticacao.api}")
    private String urlAutenticacao;


    @Override
    public void configure() throws Exception {
        from("direct:validarLogin")
                .doTry()
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.POST))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json;charset=UTF-8"))
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String responseJson = exchange.getIn().getBody(String.class);
                        JSONObject jsonObject = new JSONObject(responseJson);
                        String email = jsonObject.getString("email");
                        String senha = jsonObject.getString("senha");
                        exchange.setProperty("email", email);
                        exchange.setProperty("senha", senha);
                    }
                })
                .to(urlAutenticacao)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String respostaJson = exchange.getIn().getBody(String.class);
                        JSONObject jsonObject = new JSONObject(respostaJson);
                        String token = jsonObject.getString("token");
                        exchange.setProperty("token", token);
                    }
                })
                .end();
    }
}*/
