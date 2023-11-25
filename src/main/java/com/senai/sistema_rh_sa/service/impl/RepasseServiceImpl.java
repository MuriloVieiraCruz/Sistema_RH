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

    @Autowired
    private JReportServiceImpl jReportService;

    @Override
    public void calcularRepassesPor(HttpServletResponse response, List<Frete> freteList, Integer ano, Integer mes) {

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

        response.setContentType("application/pdf");
        DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        String dataHoraAtual = formatador.format(new Date());

        String chaveCabecalho = "Content-Disposition";
        String valorCabecalho = "attachment; filename=pdf" + dataHoraAtual + ".pdf";
        response.setHeader(chaveCabecalho, valorCabecalho);

        try {
            jReportService.exportJasperReport(response, repassesConsolidados);
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void buscarRepassesExistentes(HttpServletResponse response, Integer ano, Integer mes) {
        List<Repasse> repasses = repository.buscarRepassesPorIntevaloDe(ano, mes);

        response.setContentType("application/pdf");
        DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        String dataHoraAtual = formatador.format(new Date());

        String chaveCabecalho = "Content-Disposition";
        String valorCabecalho = "attachment; filename=pdf" + dataHoraAtual + ".pdf";
        response.setHeader(chaveCabecalho, valorCabecalho);

        try {
            jReportService.exportJasperReport(response, repasses);
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
