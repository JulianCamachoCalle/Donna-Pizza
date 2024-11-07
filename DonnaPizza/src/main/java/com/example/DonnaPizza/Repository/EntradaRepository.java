package com.example.DonnaPizza.Repository;


import com.example.DonnaPizza.Model.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada,Long> {
}
