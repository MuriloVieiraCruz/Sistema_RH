package com.senai.sistema_rh_sa.service.impl;

import com.senai.sistema_rh_sa.dto.Frete;
import com.senai.sistema_rh_sa.entity.Entregador;
import com.senai.sistema_rh_sa.entity.Repasse;
import com.senai.sistema_rh_sa.repository.RepasseRepository;
import com.senai.sistema_rh_sa.service.EntregadorService;
import com.senai.sistema_rh_sa.service.RepasseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class RepasseServiceImpl implements RepasseService {

    @Autowired
    @Qualifier("entregadorServiceImpl")
    private EntregadorService entregadorService;

    @Autowired
    private RepasseRepository repository;
    @Value("${taxa-seguro}")
    private BigDecimal taxaDeSeguro;
    @Value("${percentual-bonificacao}")
    private BigDecimal percentualDeBonificacao;

    @Override
    public List<Repasse> calcularRepassesPor(List<Frete> freteList, Integer ano, Integer mes) {

        Map<Entregador, Repasse> repassesPorEntregador = new HashMap<>();

        for (Frete frete : freteList) {

            Entregador entregador = entregadorService.buscarPor(frete.getIdEntregador());
            boolean isEntregadorPresente = repassesPorEntregador.get(entregador) != null;
            Repasse repasseDoEntregador = null;
            if (isEntregadorPresente){
                repasseDoEntregador = repassesPorEntregador.get(entregador);
                BigDecimal valorBrutoFinal = repasseDoEntregador.getValorBruto().add(frete.getFrete());
                repasseDoEntregador.setValorBruto(valorBrutoFinal);
                repasseDoEntregador.setQuantidadeDeEntregas(repasseDoEntregador.getQuantidadeDeEntregas() + 1);
            }else{
                repasseDoEntregador = new Repasse();
                repasseDoEntregador.setValorBruto(frete.getFrete());
                repasseDoEntregador.setDataMoviementacao(frete.getDataMovimento());
                repasseDoEntregador.setAno(ano);
                repasseDoEntregador.setMes(mes);
            }
            repassesPorEntregador.put(entregador, repasseDoEntregador);

        }

        List<Repasse> repassesConsolidados = new ArrayList<>();
        repassesPorEntregador.forEach((e, re) -> {
            re.setEntregador(e);
            repassesConsolidados.add(re);
        });

        /*Collections.sort(repassesConsolidados, new Comparator<Repasse>() {
            @Override
            public int compare(Repasse repasse1, Repasse repasse2) {
                return -Integer.compare(repasse1.getQuantidadeDeEntregas(), repasse2.getQuantidadeDeEntregas());
            }
        });*/

        for (Repasse repasse : repassesConsolidados){
            BigDecimal valorLiquido = repasse.getValorBruto().subtract(taxaDeSeguro);
            BigDecimal divisor = new BigDecimal(100);
            BigDecimal percentual = percentualDeBonificacao.divide(divisor);
            BigDecimal valorBonificacao = valorLiquido.multiply(percentual);
            BigDecimal valorLiquidoFinal = valorLiquido.add(valorBonificacao);
            repasse.setValorLiquido(valorLiquidoFinal);
            repasse.setBonificacao(percentualDeBonificacao);
            repasse.setTaxaSeguroDeVida(taxaDeSeguro);
            repository.save(repasse);
        }
        return repassesConsolidados;
    }
}