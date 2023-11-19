package com.senai.sistema_rh_sa.controller;

import com.senai.sistema_rh_sa.dto.InformacoesLogin;
import com.senai.sistema_rh_sa.security.GerenciadorDeTokenJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private GerenciadorDeTokenJwt gerenciadorDeToken;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> logar(@RequestBody InformacoesLogin infoLogin){
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                infoLogin.getLogin(), infoLogin.getSenha()));
        String tokenGerado = gerenciadorDeToken.gerarTokenPor(infoLogin.getLogin());
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("token", tokenGerado);
        return ResponseEntity.ok(response);
    }

}
