package com.senai.sistema_rh_sa.repository;

import com.senai.sistema_rh_sa.entity.Entregador;
import com.senai.sistema_rh_sa.entity.Repasse;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static com.google.common.base.CharMatcher.any;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class RepasseRepositoryTest {

    @Autowired
    RepasseRepository repository;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        Repasse repasseTeste = new Repasse();
        repasseTeste.setId(1);
        repasseTeste.setMes(11);
        repasseTeste.setAno(2023);
        repasseTeste.setValorLiquido(new BigDecimal(300));
        repasseTeste.setValorBruto(new BigDecimal(500));
        repasseTeste.setBonificacao(new BigDecimal(50));
        repasseTeste.setTaxaSeguroDeVida(new BigDecimal(60));
        repasseTeste.setDataMoviementacao(Instant.now());
        repasseTeste.setDataMoviementacao(Instant.now());
        repasseTeste.setQuantidadeDeEntregas(10);
        repasseTeste.setEntregador(new Entregador());

        this.entityManager.persist(repasseTeste);
    }

    @Nested
    class BuscarPor {

        @Test
        @DisplayName("Deve buscar os repasses com sucesso do banco no intervalo de tempo informado")
        void BuscarRepasseCaso1() {

            List<Repasse> repasses = repository.buscarRepassesPorIntevaloDe(2023, 11);
            assertThat(repasses).isNotNull();
        }

        @Test
        @DisplayName("Deve contar o repasse com sucesso do banco pelo cpf")
        void BuscarRepasseCaso2() {

            Integer qtdeDeRepassesVinculados = repository.contarRepassesVinculadosPor(1);
            assertThat(qtdeDeRepassesVinculados).isNotNull();
        }

        @Test
        @DisplayName("Deve verificar se já existe repasse gerado no banco para o intervalo informado")
        void BuscarRepasseCaso3() {

            Boolean isRepassesExistentes = repository.verificaBuscaPorDadosExistentesNoBanco(2023, 11);
            assertThat(isRepassesExistentes).isNotNull();
        }
    }
}
