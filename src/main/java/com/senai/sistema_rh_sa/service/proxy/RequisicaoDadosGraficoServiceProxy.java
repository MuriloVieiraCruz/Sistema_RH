//package com.senai.sistema_rh_sa.service.proxy;
//
//import com.senai.sistema_rh_sa.service.RequisicaoDadosGraficoService;
//import org.apache.camel.ProducerTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class RequisicaoDadosGraficoServiceProxy implements RequisicaoDadosGraficoService {
//
//    @Autowired
//    @Qualifier("requisicaoDadosDoGraficoServiceImpl")
//    private RequisicaoDadosGraficoService requisicaoDadosGraficoService;
//
//    @Autowired
//    private ProducerTemplate toApiFrete;
//
//    @Override
//    public List<?> requisitarDadosPor(Integer mes, Integer ano) {
//        //TODO Verificar a implementação nessa parte de código
////        Filtro filtro = montarFiltroPor(mes, ano);
////        List<Frete> freteList = (List<Frete>) this.toApiFrete.sendBody("direct:enviarRequisicao", null, filtro);
////        List<Repasse> repasseList = this.repasseService.calcularRepassesPor(freteList);
////        return repasseList;
//        return null;
//    }
//
//
//}
