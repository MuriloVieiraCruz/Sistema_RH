package com.senai.sistema_rh_sa.repository;

import com.senai.sistema_rh_sa.model.Entregador;
import com.senai.sistema_rh_sa.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EntregadorRepository extends JpaRepository<Entregador, Integer> {

    @Query(value = "SELECT Count(e) FROM Entregador e WHERE e.cpf = :cpf ;")
    public Integer contarPor(String cpf);

    @Query(value = "SELECT e FROM Entregador e WHERE Upper(e.nome) LIKE Upper(:nome) ;")
    public Page<Entregador> listarPor(String nome, Page paginacao);

    @Query(value = "UPDATE Entregador e SET e.status = :status WHERE e.id = :id ;")
    public void alterarStatusPor(Integer id, Status status);
}
