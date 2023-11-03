package com.senai.sistema_rh_sa.service.exception;

public class MetodoNaoSuportadoException extends RuntimeException{
    public MetodoNaoSuportadoException(String message) {
        super(message);
    }
}
