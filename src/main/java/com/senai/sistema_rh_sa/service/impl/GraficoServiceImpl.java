package com.senai.sistema_rh_sa.service.impl;

import com.senai.sistema_rh_sa.entity.DadosDoGrafico;
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
    public Map<Integer, DadosDoGrafico> calcularDadosPor(Integer ano, Integer mes) {
        List<Repasse> listaDeRepasses = repository.buscarRepassesPorIntevaloDe(ano, mes);
        Map<Integer, DadosDoGrafico> dadosDoGraficoConsolidados = calcularDadosDoGraficoPor(listaDeRepasses, ano, mes);
        return dadosDoGraficoConsolidados;
    }

    //TODO Verificar se o cálculo do gráfico está pegando certinho

    private Map<Integer, DadosDoGrafico> calcularDadosDoGraficoPor(List<Repasse> repasses, Integer ano, Integer mes) {
        Map<Integer, DadosDoGrafico> dadosConsoliados = null;

        Collections.sort(repasses, new Comparator<Repasse>() {
            @Override
            public int compare(Repasse repasse1, Repasse repasse2) {
                return Integer.compare(repasse1.getMes(), repasse2.getMes());
            }
        });

        if (mes == null) {
            dadosConsoliados = new HashMap<>();
            for (Repasse repasse : repasses) {

                boolean isRepassePresente = dadosConsoliados.get(repasse.getMes()) != null;
                DadosDoGrafico dado = null;

                if (isRepassePresente) {
                    dado = dadosConsoliados.get(repasse.getMes());
                    BigDecimal valorFinal = dado.getVolumeMovimentadoDeRepasses().add(repasse.getValorBruto());
                    dado.setVolumeMovimentadoDeRepasses(valorFinal);
                } else {
                    dado = new DadosDoGrafico();
                    dado.setAno(repasse.getAno());
                    dado.setMes(repasse.getMes());
                    dado.setVolumeMovimentadoDeRepasses(repasse.getValorBruto());
                }
                dadosConsoliados.put(repasse.getMes(), dado);
            }
        } else {
            dadosConsoliados = new HashMap<>();
            Integer indice = 0;

            YearMonth yearMonth = YearMonth.of(ano, mes);
            LocalDate primeiroDiaDoMes = yearMonth.atDay(1);
            LocalDate ultimoDiaDoMes = yearMonth.atEndOfMonth();

            LocalDate dataAtual = primeiroDiaDoMes;

            while (!dataAtual.isAfter(ultimoDiaDoMes) && indice < repasses.size()) {

                int semanaDoMes = dataAtual.get(WeekFields.of(Locale.getDefault()).weekOfMonth());

                LocalDate dataMovimentacao = repasses.get(indice).getDataMoviementacao()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                if (dataMovimentacao.getDayOfMonth() == dataAtual.getDayOfMonth()) {
                    DadosDoGrafico dados = dadosConsoliados.getOrDefault(semanaDoMes, new DadosDoGrafico());
                    if (dados.getVolumeMovimentadoDeRepasses() == null) {
                        dados.setVolumeMovimentadoDeRepasses(repasses.get(indice).getValorBruto());
                        dados.setAno(ano);
                        dados.setMes(mes);
                    } else {
                        BigDecimal valorAtual = dados.getVolumeMovimentadoDeRepasses();
                        BigDecimal novoValor = valorAtual.add(repasses.get(indice).getValorBruto());
                        dados.setVolumeMovimentadoDeRepasses(novoValor);
                    }

                    dadosConsoliados.put(semanaDoMes, dados);
                    indice++;
                }

                dataAtual = dataAtual.plusDays(1);
            }

            int totalSemanasNoMes = (ultimoDiaDoMes.getDayOfMonth() - 1) / 7 + 1;
            for (int i = 1; i <= totalSemanasNoMes; i++) {
                dadosConsoliados.putIfAbsent(i, new DadosDoGrafico());
            }
        }
        return dadosConsoliados;
    }

}
