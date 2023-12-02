package com.senai.sistema_rh_sa.service.impl;

import com.senai.sistema_rh_sa.dto.DadosGrafico;
import com.senai.sistema_rh_sa.dto.Mes;
import com.senai.sistema_rh_sa.entity.Repasse;
import com.senai.sistema_rh_sa.repository.RepasseRepository;
import com.senai.sistema_rh_sa.service.GraficoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.WeekFields;
import java.util.*;

@Service
public class GraficoServiceImpl implements GraficoService {

    @Autowired
    private RepasseRepository repository;

    @Override
    public List<DadosGrafico> calcularDadosPor(Integer ano, Integer mes) {
        List<Repasse> listaDeRepasses = repository.buscarRepassesPorIntevaloDe(ano, mes);
        List<DadosGrafico> dadosDoGraficoConsolidados = calcularDadosDoGraficoPor(listaDeRepasses, ano, mes);
        return dadosDoGraficoConsolidados;
    }

    //TODO Verificar se o cálculo do gráfico está pegando certinho

    private List<DadosGrafico> calcularDadosDoGraficoPor(List<Repasse> repasses, Integer ano, Integer mes) {
        Map<Integer, DadosGrafico> dadosConsolidados = null;

        repasses.sort(Comparator.comparingInt(Repasse::getMes));

        if (mes == null) {
            dadosConsolidados = new HashMap<>();
            for (Repasse repasse : repasses) {

                boolean isRepassePresente = dadosConsolidados.get(repasse.getMes()) != null;
                DadosGrafico dado = null;

                if (isRepassePresente) {
                    dado = dadosConsolidados.get(repasse.getMes());
                    BigDecimal valorFinal = dado.getMes().getVolumeMovimentadoDeRepasses().add(repasse.getValorBruto());
                    dado.getMes().setVolumeMovimentadoDeRepasses(valorFinal);
                } else {
                    dado = new DadosGrafico();
                    Mes mesDoAno = new Mes();
                    mesDoAno.setNome(Month.of(repasse.getMes()).toString().toLowerCase());
                    mesDoAno.setVolumeMovimentadoDeRepasses(repasse.getValorBruto());
                    dado.setAno(repasse.getAno());
                    dado.setMes(mesDoAno);
                }
                dadosConsolidados.put(repasse.getMes(), dado);
            }

            List<DadosGrafico> dadosListados = new ArrayList<>();
            dadosConsolidados.forEach((e, dados) -> {
                dadosListados.add(dados);
            });
            return dadosListados;

        } else {
//            dadosConsolidados = new HashMap<>();
//            Integer indice = 0;
//
//            YearMonth yearMonth = YearMonth.of(ano, mes);
//            LocalDate primeiroDiaDoMes = yearMonth.atDay(1);
//            LocalDate ultimoDiaDoMes = yearMonth.atEndOfMonth();
//
//            LocalDate dataAtual = primeiroDiaDoMes;
//
//            while (!dataAtual.isAfter(ultimoDiaDoMes) && indice < repasses.size()) {
//
//                int semanaDoMes = dataAtual.get(WeekFields.of(Locale.getDefault()).weekOfMonth());
//
//                LocalDate dataMovimentacao = repasses.get(indice).getDataMoviementacao()
//                        .atZone(ZoneId.systemDefault())
//                        .toLocalDate();
//
//                if (dataMovimentacao.getDayOfMonth() == dataAtual.getDayOfMonth()) {
//                    DadosDoGrafico dados = dadosConsoliados.getOrDefault(semanaDoMes, new DadosDoGrafico());
//                    if (dados.getVolumeMovimentadoDeRepasses() == null) {
//                        dados.setVolumeMovimentadoDeRepasses(repasses.get(indice).getValorBruto());
//                        dados.setAno(ano);
//                        dados.setMes(mes);
//                    } else {
//                        BigDecimal valorAtual = dados.getVolumeMovimentadoDeRepasses();
//                        BigDecimal novoValor = valorAtual.add(repasses.get(indice).getValorBruto());
//                        dados.setVolumeMovimentadoDeRepasses(novoValor);
//                    }
//
//                    dadosConsoliados.put(semanaDoMes, dados);
//                    indice++;
//                }
//
//                dataAtual = dataAtual.plusDays(1);
//            }
//
//            int totalSemanasNoMes = (ultimoDiaDoMes.getDayOfMonth() - 1) / 7 + 1;
//            for (int i = 1; i <= totalSemanasNoMes; i++) {
//                dadosConsoliados.putIfAbsent(i, new DadosDoGrafico());
//            }
        }
        return null;
    }

}
