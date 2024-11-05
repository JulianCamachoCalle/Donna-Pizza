package com.example.DonnaPizza.Repository;

import com.example.DonnaPizza.Model.Pastas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PastaRespository extends JpaRepository<Pastas,Long> {
}
