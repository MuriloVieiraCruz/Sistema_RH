package com.senai.sistema_rh_sa.service.impl;

import com.senai.sistema_rh_sa.dto.Frete;
import com.senai.sistema_rh_sa.entity.Entregador;
import com.senai.sistema_rh_sa.entity.Repasse;
import com.senai.sistema_rh_sa.repository.RepasseRepository;
import com.senai.sistema_rh_sa.service.EntregadorService;
import com.senai.sistema_rh_sa.service.RepasseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class RepassesServiceImpl implements RepasseService {

    @Autowired
    @Qualifier("entregadorServiceImpl")
    private EntregadorService entregadorService;

    @Autowired
    private RepasseRepository repository;

    @Override
    public List<Repasse> calcularRepassesPor(List<Frete> freteList) {

        List<Repasse> listaDeRepasses = new ArrayList<>();
        Repasse repasse;
        BigDecimal valorBruto;
        BigDecimal valorBrutoFinal

        for (Frete frete : freteList) {

            repasse = new Repasse();
            Entregador entregador = entregadorService.buscarPor(frete.getIdEntregador());
            repasse.setEntregador(entregador);
            Integer qtdeDeEntregas = 1;
            valorBruto = frete.getFrete();
            valorBrutoFinal = null;

            for (Frete frete1 : freteList) {
                if (entregador.getId().equals(frete1.getIdEntregador())) {
                    qtdeDeEntregas += 1;
                    valorBrutoFinal = valorBruto.add(frete1.getFrete());

                    freteList.remove(frete);
                }
            }
            //Retirar aqui do valor bruto o valor do seguro de vida
            //Fazer um Sort para organizar lista em ordem de quantidade de etregas para dar bonificação
            repasse.setQuantidadeDeEntregas(qtdeDeEntregas);
            repasse.setValorBruto(valorBrutoFinal);
            repasse.setDataMoviementacao(Instant.now());
            repasse.setDataPagamento(Instant.now());
            Repasse repasseSalvo = repository.saveAndFlush(repasse);
            listaDeRepasses.add(repasseSalvo);
        }
        return listaDeRepasses;
    }
}
