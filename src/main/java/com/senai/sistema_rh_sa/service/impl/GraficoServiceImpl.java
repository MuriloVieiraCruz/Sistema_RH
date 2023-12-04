package com.senai.sistema_rh_sa.service.impl;

import com.senai.sistema_rh_sa.dto.AnoDeRepasse;
import com.senai.sistema_rh_sa.dto.MesDeRepasse;
import com.senai.sistema_rh_sa.entity.Repasse;
import com.senai.sistema_rh_sa.repository.RepasseRepository;
import com.senai.sistema_rh_sa.service.GraficoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.*;

@Service
public class GraficoServiceImpl implements GraficoService {

    @Autowired
    private RepasseRepository repository;

    @Override
    public AnoDeRepasse calcularDadosPor(Integer ano, Integer mes) {
        List<Repasse> listaDeRepasses = repository.buscarRepassesPorIntevaloDe(ano, mes);
        AnoDeRepasse repasseAnual = calcularDadosDoGraficoPor(listaDeRepasses, ano, mes);
        return repasseAnual;
    }

    @Override
    public AnoDeRepasse calcularDadosPor(Integer ano) {
        List<Repasse> listaDeRepasses = repository.buscarRepassesPorIntevaloDe(ano, null);
        AnoDeRepasse repasseAnual = calcularDadosDoGraficoPor(listaDeRepasses, ano, null);
        return repasseAnual;
    }

    //TODO Verificar se o cálculo do gráfico está pegando certinho

    private AnoDeRepasse calcularDadosDoGraficoPor(List<Repasse> repasses, Integer ano, Integer mes) {
        Map<Integer, MesDeRepasse> mesesConsolidados = null;

        repasses.sort(Comparator.comparingInt(Repasse::getMes));

        if (mes == null) {
            mesesConsolidados = new HashMap<>();
            for (Repasse repasse : repasses) {

                boolean isRepassePresente = mesesConsolidados.get(repasse.getMes()) != null;
                MesDeRepasse repasseMensal = null;

                if (isRepassePresente) {
                    repasseMensal = mesesConsolidados.get(repasse.getMes());
                    BigDecimal valorFinal = repasseMensal.getValorRepassado().add(repasse.getValorBruto());
                    repasseMensal.setValorRepassado(valorFinal);
                } else {
                    repasseMensal = new MesDeRepasse();
                    repasseMensal.setNome(Month.of(repasse.getMes())
                            .getDisplayName(TextStyle.FULL, new Locale("pt", "BR"))
                            .toString()
                            .toLowerCase());
                    repasseMensal.setValorRepassado(repasse.getValorBruto());
                }
                mesesConsolidados.put(repasse.getMes(), repasseMensal);
            }

            AnoDeRepasse repasseAnual = new AnoDeRepasse();
            repasseAnual.setAno(ano);
            mesesConsolidados.forEach((e, mesAtual) -> {
                repasseAnual.getMeses().add(mesAtual);
            });
            return repasseAnual;

        } else {
//            mesesConsolidados = new HashMap<>();
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
