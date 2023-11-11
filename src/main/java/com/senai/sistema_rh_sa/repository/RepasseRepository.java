package com.senai.sistema_rh_sa.repository;

import com.senai.sistema_rh_sa.entity.Repasse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepasseRepository extends JpaRepository<Repasse, Integer> {

    @Query(value = "SELECT r " +
            "FROM Repasse r " +
            "WHERE r.ano = :ano " +
            "AND (:mes IS NULL OR r.mes = :mes) ")
    public List<Repasse> buscarRepassesPorIntevaloDe(Integer ano, Integer mes);

}
