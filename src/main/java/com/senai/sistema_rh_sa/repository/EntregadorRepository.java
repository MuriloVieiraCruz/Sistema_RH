package com.senai.sistema_rh_sa.repository;

import com.senai.sistema_rh_sa.entity.Entregador;
import com.senai.sistema_rh_sa.entity.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EntregadorRepository extends JpaRepository<Entregador, Integer> {

    @Query(value = "SELECT e FROM Entregador e WHERE e.cpf = :cpf ")
    public Entregador buscarPorCPF(String cpf);

    @Query(value = "SELECT e FROM Entregador e WHERE e.telefone = :telefone ")
    public Entregador buscarPorTelefone(String telefone);

    @Query(value = "SELECT e FROM Entregador e WHERE e.numeroHabilitacao = :numeroHabilitacao ")
    public Entregador buscarPorCNH(String numeroHabilitacao);

    @Query(value = "SELECT e "
            + "FROM Entregador e "
            + "WHERE Upper(e.nome) LIKE Upper(:nome) "
            + "AND (:cpf IS NULL OR e.cpf = :cpf) "
            + "AND (:numeroHabilitacao IS NULL OR e.numeroHabilitacao = :numeroHabilitacao) "
            + "AND (:telefone IS NULL OR e.telefone = :telefone)",
    countQuery = "SELECT e "
            + "FROM Entregador e "
            + "WHERE Upper(e.nome) LIKE Upper(:nome) "
            + "AND (:cpf IS NULL OR e.cpf = :cpf) "
            + "AND (:numeroHabilitacao IS NULL OR e.numeroHabilitacao = :numeroHabilitacao) "
            + "AND (:telefone IS NULL OR e.telefone = :telefone)")
    public Page<Entregador> listarPor(String nome, String cpf, String numeroHabilitacao, String telefone, Pageable paginacao);

    @Query(value = "SELECT e.id FROM Entregador e WHERE e.email = :email ")
    public Integer buscarIdPor(String email);

    @Modifying
    @Query(value = "UPDATE Entregador e SET e.status = :status WHERE e.id = :id ")
    public void alterarStatusPor(Integer id, Status status);
}
