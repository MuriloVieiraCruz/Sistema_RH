//package com.senai.sistema_rh_sa.integracao.rota;
//
//import com.fasterxml.jackson.databind.JavaType;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.type.TypeFactory;
//import com.senai.sistema_rh_sa.dto.Frete;
//import com.senai.sistema_rh_sa.integracao.processor.ErrorProcessor;
//
//import java.lang.reflect.Type;
//import java.util.List;
//
//import org.apache.camel.Exchange;
//import org.apache.camel.Processor;
//import org.apache.camel.builder.RouteBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.apache.camel.component.http.HttpMethods;
//
//@Component
//public class ToApiFrete extends RouteBuilder {
//
////    @Value("${url.requisicao.frete.api}")
////    private String urlDeRequisicao;
//
//    @Autowired
//    private ErrorProcessor errorProcessor;
//
//    private List<Frete> freteList;
//
//    @Override
//    public void configure() throws Exception {
//        from("direct:enviarRequisicao")
//                .doTry()
//                .setHeader(Exchange.HTTP_METHOD, HttpMethods.GET)
//                .to(urlDeRequisicao)
//                .process(exchange -> {
//                    String responseBody = exchange.getIn().getBody(String.class);
//
//                    ObjectMapper objectMapper = new ObjectMapper();
//                    JavaType freteTypeList = TypeFactory.defaultInstance().constructCollectionType(List.class, Frete.class);
//                    freteList = objectMapper.readValue(responseBody, freteTypeList);
//
//                    exchange.getIn().setBody(freteTypeList);
//                })
//                .doCatch(Exception.class)
//                .setProperty("error", simple("${exception"))
//                .process(errorProcessor)
//                .end();
//    }
//}
