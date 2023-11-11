/*package com.senai.sistema_rh_sa.integracao.rota;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.senai.sistema_rh_sa.dto.Frete;
import com.senai.sistema_rh_sa.integracao.processor.ErrorProcessor;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.util.json.JsonObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.camel.component.http.HttpMethods;

@Component
public class ToApiFrete extends RouteBuilder {

    @Value("${url.requisicao.frete.api}")
    private String urlDeRequisicao;
    @Value("${url.requisicao.token.api}")
    private String urlToken;
    @Value("${username.api}")
    private String username;
    @Value("${password.api}")
    private String password;

    @Autowired
    private ErrorProcessor errorProcessor;

    @Override
    public void configure() throws Exception {
        from("direct:enviarRequisicao")
                .doTry()
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.POST))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json;charset=UTF-8"))
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        JsonObject requestBody = new JsonObject();
                        requestBody.put("login", username);
                        requestBody.put("senha", password);
                        exchange.getMessage().setBody(requestBody);
                    }
                })
                .to(urlToken)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String respostaJson = exchange.getIn().getBody(String.class);
                        JSONObject jsonObject = new JSONObject(respostaJson);
                        String token = jsonObject.getString("token");
                        exchange.setProperty("token", token);
                    }
                })
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json;charset=UTF-8"))
                .setHeader("Authorization", simple("Bearer ${exchangeProperty.token}"))
                .to(urlDeRequisicao)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String jsonBody = exchange.getMessage().getBody(String.class);
                        JSONObject jsonObject = new JSONObject(jsonBody);
                        exchange.getMessage().setBody(jsonObject);
                    }
                })
                .doCatch(Exception.class)
                .setProperty("error", simple("${exception}"))
                .process(errorProcessor)
                .end();
    }
}*/
