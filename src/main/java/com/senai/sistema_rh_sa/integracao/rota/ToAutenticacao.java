/*
package com.senai.sistema_rh_sa.integracao.rota;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpMethods;
import org.apache.camel.util.json.JsonObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ToAutenticacao extends RouteBuilder {

    @Value("${url.requisicao.token.api}")
    private String urlToken;
    @Value("${username.api}")
    private String username;
    @Value("${password.api}")
    private String password;


    @Override
    public void configure() throws Exception {
        from("direct:atenticacao")
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
                .end();
    }
}
*/
