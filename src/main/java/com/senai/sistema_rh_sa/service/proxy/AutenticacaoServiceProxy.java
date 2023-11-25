package com.senai.sistema_rh_sa.service.proxy;

import com.senai.sistema_rh_sa.dto.Frete;
import com.senai.sistema_rh_sa.entity.Repasse;
import com.senai.sistema_rh_sa.service.AutenticacaoService;
import com.senai.sistema_rh_sa.service.RepasseService;
import org.apache.camel.ProducerTemplate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AutenticacaoServiceProxy implements AutenticacaoService {
    
    @Autowired
    private ProducerTemplate toApiAuthentication;

    @Override
    public String autenticarUsuario(String login, String senha) {
        JSONObject requestBodyCredenciais = new JSONObject();
        requestBodyCredenciais.put("email", login);
        requestBodyCredenciais.put("senha", senha);
        JSONObject jsonObject = toApiAuthentication.requestBody("direct:validarLogin", requestBodyCredenciais, JSONObject.class);
        String token = jsonObject.getString("token");
        return token;
    }


}
