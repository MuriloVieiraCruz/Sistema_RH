package com.senai.sistema_rh_sa.service.impl;

import com.senai.sistema_rh_sa.dto.Frete;
import com.senai.sistema_rh_sa.entity.Entregador;
import com.senai.sistema_rh_sa.entity.Repasse;
import com.senai.sistema_rh_sa.repository.RepasseRepository;
import com.senai.sistema_rh_sa.service.EntregadorService;
import com.senai.sistema_rh_sa.service.RepasseService;
import com.senai.sistema_rh_sa.service.exception.MetodoNaoSuportadoException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import net.sf.jasperreports.engine.JRException;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
                BigDecimal valorBrutoFinal = repasseDoEntregador.getValorBruto().add(frete.getValorTotal());
                repasseDoEntregador.setValorBruto(valorBrutoFinal);
                repasseDoEntregador.setQuantidadeDeEntregas(repasseDoEntregador.getQuantidadeDeEntregas() + 1);
            } else{
                repasseDoEntregador = new Repasse();
                repasseDoEntregador.setValorBruto(frete.getValorTotal());
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

        repassesConsolidados.sort(Comparator.comparingInt(Repasse::getQuantidadeDeEntregas).reversed());

        int contador = 0;
        for (Repasse repasse : repassesConsolidados){
            BigDecimal valorLiquido = repasse.getValorBruto().subtract(taxaDeSeguro).setScale(2, RoundingMode.CEILING);
            if (contador < 3) {
                BigDecimal divisor = new BigDecimal(100);
                BigDecimal percentual = percentualDeBonificacao.divide(divisor);
                BigDecimal valorBonificacao = valorLiquido.multiply(percentual);
                BigDecimal valorLiquidoFinal = valorLiquido.add(valorBonificacao).setScale(2, RoundingMode.CEILING);
                repasse.setValorLiquido(valorLiquidoFinal);
                repasse.setBonificacao(percentualDeBonificacao);
                BigDecimal reducao = new BigDecimal(35);
                percentualDeBonificacao = valorBonificacao.subtract(reducao);
            } else {
                repasse.setBonificacao(new BigDecimal(0));
                repasse.setValorLiquido(valorLiquido);
            }

            contador++;
            repasse.setTaxaSeguroDeVida(taxaDeSeguro);
            repository.save(repasse);
        }

        return repassesConsolidados;
    }

    @Override
    public List<Repasse> buscarRepassesExistentes(Integer ano, Integer mes) {
        return repository.buscarRepassesPorIntevaloDe(ano, mes);
    }
}
