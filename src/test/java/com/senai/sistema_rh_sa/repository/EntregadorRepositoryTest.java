package com.senai.sistema_rh_sa.repository;

import com.senai.sistema_rh_sa.entity.Endereco;
import com.senai.sistema_rh_sa.entity.Entregador;
import com.senai.sistema_rh_sa.entity.enums.Status;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class EntregadorRepositoryTest {

    @Autowired
    EntregadorRepository repository;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        Entregador entregadorTeste = new Entregador();
        entregadorTeste.setId(1);
        entregadorTeste.setEmail("Murilo@gmail.com");
        entregadorTeste.setCpf("073.945.759-42");
        entregadorTeste.setNumeroHabilitacao("12365489751");
        entregadorTeste.setSeguroDeVida(true);
        Endereco endereco = new Endereco();
        endereco.setEstado("Teste");
        endereco.setCidade("Teste");
        endereco.setBairro("Teste");
        endereco.setLogradouro("Teste");
        entregadorTeste.setEndereco(endereco);
        entregadorTeste.setNome("Murilo");
        entregadorTeste.setTelefone("(48) 99802-8116");

        this.entityManager.persist(entregadorTeste);
    }

    @Nested
    class BuscarPor {

        @Test
        @DisplayName("Deve buscar o entregador com sucesso do banco pelo e-mail")
        void BuscarEntregadorCaso1() {

            Entregador entregador = repository.buscarPor("teste@gmail.com");
            assertThat(entregador).isNotNull();
        }

        @Test
        @DisplayName("Deve buscar o entregador com sucesso do banco pelo cpf")
        void BuscarEntregadorCaso2() {

            Entregador entregador = repository.buscarPorCPF("073.945.759-42");
            assertThat(entregador).isNotNull();
        }

        @Test
        @DisplayName("Deve buscar o entregador com sucesso do banco pelo telefone")
        void BuscarEntregadorCaso3() {

            Entregador entregador = repository.buscarPorTelefone("(48)99999-9999");
            assertThat(entregador).isNotNull();
        }

        @Test
        @DisplayName("Deve buscar o entregador com sucesso do banco pelo número da habilitação")
        void BuscarEntregadorCaso4() {

            Entregador entregador = repository.buscarPorNumeroHabilitacao("79463285911");
            assertThat(entregador).isNotNull();
        }
    }

    @Nested
    class ListarPor {

        @Test
        @DisplayName("Deve listar os entregadores pelo nome")
        void listaEntregadoresCaso1() {

            Page<Entregador> page = repository.listarPor("muri", null, null, null, null, PageRequest.of(0, 15));
            assertThat(page).isNotNull();
            assertThat(page.getTotalPages()).isEqualTo(0);
            assertThat(page.getNumber()).isEqualTo(0);
        }

        @Test
        @DisplayName("Deve listar os entregadores pelo nome, cpf, email, numeroHabilitacao e telefone")
        void listaEntregadoresCaso2() {

            Page<Entregador> page = repository.listarPor(
                    "muri",
                    "005.900.289-10".describeConstable(),
                    "325.603.580-93".describeConstable(),
                    "79463285911".describeConstable(),
                    "(48) 998028116".describeConstable(),
                    PageRequest.of(0, 15));
            assertThat(page).isNotNull();
            assertThat(page.getTotalPages()).isEqualTo(0);
            assertThat(page.getNumber()).isEqualTo(0);
        }

        @Test
        @DisplayName("Não deve retornar entregador caso parâmetros inexistentes")
        void listEnterpriseByNameCase2() {

            Page<Entregador> page = repository.listarPor("www", null, null, null, null, PageRequest.of(0, 15));
            assertThat(page.getContent()).isEmpty();
            assertThat(page.getTotalElements()).isZero();
        }
    }

    @Nested
    class AtualizarStatusPor {

        @Test
        @DisplayName("Deve alterar o status do entregador no banco")
        void alterarStatusCaso1() {

            repository.alterarStatusPor(1,  Status.I);
            Entregador entregadorAlterado = repository.findById(1).orElse(null);

            assertThat(entregadorAlterado).isNotNull();
            assertThat(entregadorAlterado.getStatus()).isEqualByComparingTo(Status.I);
        }

        @Test
        @DisplayName("Não deve alterar o status do entregador no banco")
        void alterarStatusCaso2() {

            repository.alterarStatusPor(20,  Status.I);
            Entregador entregadorAlterado = repository.findById(20).orElse(null);

            assertThat(entregadorAlterado).isNull();
        }
    }
}
