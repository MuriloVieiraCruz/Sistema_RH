package com.senai.sistema_rh_sa.integracao.rota;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpMethods;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ToAutenticacao extends RouteBuilder {

    @Value("${url.requisicao.token.logistica}")
    private String urlToken;
    @Value("${username.logistica}")
    private String login;
    @Value("${password.logistica}")
    private String senha;


    @Override
    public void configure() throws Exception {
        from("direct:autenticacaoLogistica")
                .doTry()
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.POST))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json;charset=UTF-8"))
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        JSONObject requestBody = new JSONObject();
                        requestBody.put("login", login);
                        requestBody.put("senha", senha);
                        exchange.getMessage().setBody(requestBody.toString());
                    }
                })
                .to(urlToken)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String respostaJson = exchange.getMessage().getBody(String.class);
                        JSONObject jsonObject = new JSONObject(respostaJson);
                        String token = jsonObject.getString("token");
                        exchange.setProperty("token", token);
                    }
                })
                .end();
    }
}
