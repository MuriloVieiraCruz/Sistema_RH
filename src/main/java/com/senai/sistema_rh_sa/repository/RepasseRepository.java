package com.senai.sistema_rh_sa.repository;

import com.senai.sistema_rh_sa.entity.Repasse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepasseRepository extends JpaRepository<Repasse, Integer> {



}
