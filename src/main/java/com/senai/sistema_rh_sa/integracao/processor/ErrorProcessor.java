package com.senai.sistema_rh_sa.integracao.processor;

import com.senai.sistema_rh_sa.excecoes.IntegracaoException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.lang.annotation.Annotation;

@Component
public class ErrorProcessor implements Processor, Serializable {

    @Override
    public void process(Exchange exchange) throws Exception {
        Exception error = exchange.getProperty("error", Exception.class);
        error.printStackTrace();
        throw new IntegracaoException(error.getMessage());
    }
}
