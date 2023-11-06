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
import java.util.Collections;
import java.util.Comparator;
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
        Repasse repasse = null;
        BigDecimal valorLiquido;
        BigDecimal valorBruto;
        BigDecimal valorBrutoFinal = null;
        Integer qtdeDeEntregas;

        for (Frete frete : freteList) {

            repasse = new Repasse();
            Entregador entregador = entregadorService.buscarPor(frete.getIdEntregador());
            repasse.setEntregador(entregador);
            qtdeDeEntregas = 1;
            valorBruto = frete.getFrete();

            for (Frete frete1 : freteList) {
                if (entregador.getId().equals(frete1.getIdEntregador())) {
                    qtdeDeEntregas += 1;
                    valorBrutoFinal = valorBruto.add(frete1.getFrete());
                    freteList.remove(frete);
                }
            }

            if (entregador.getSeguroDeVida()) {
                BigDecimal divisor = new BigDecimal(100);
                BigDecimal percentualDaTaxaDeSeguro = repasse.getPercentualSeguroDeVida();
                BigDecimal valorDescontado = valorBrutoFinal
                        .multiply(percentualDaTaxaDeSeguro)
                        .divide(divisor);
                repasse.setTaxaSeguroDeVida(valorDescontado);
            }

            valorLiquido = valorBrutoFinal.subtract(repasse.getTaxaSeguroDeVida());
            repasse.setValorLiquido(valorLiquido);
            repasse.setQuantidadeDeEntregas(qtdeDeEntregas);
            repasse.setValorBruto(valorBrutoFinal);
            valorBrutoFinal = null;
            valorBruto = null;
            valorLiquido = null;
            Repasse repasseSalvo = repository.save(repasse);
            listaDeRepasses.add(repasseSalvo);
        }

        Collections.sort(listaDeRepasses, new Comparator<Repasse>() {
            @Override
            public int compare(Repasse repasse1, Repasse repasse2) {
                return Integer.compare(repasse1.getQuantidadeDeEntregas(), repasse2.getQuantidadeDeEntregas());
            }
        });

        BigDecimal bonificacao = new BigDecimal(50);

        for (int i = 0; i < 3; i++) {
            repasse.setValorLiquido(repasse.getValorLiquido().add(bonificacao));
            bonificacao.subtract(new BigDecimal(12));
        }
        List<Repasse> listaDeRepassesSalvos = repository.saveAllAndFlush(listaDeRepasses);
        return listaDeRepassesSalvos;
        //falta setar o atributo ano e mês
    }
}
