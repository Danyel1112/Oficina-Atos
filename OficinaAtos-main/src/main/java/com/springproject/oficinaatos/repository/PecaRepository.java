package com.springproject.oficinaatos.repository;

import com.springproject.oficinaatos.model.Peca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PecaRepository extends JpaRepository<Peca, Long> {
    @Query("SELECT p FROM Peca p WHERE p.nome LIKE %:nome%")
    List<Peca> findByNomeContaining(@Param("nome") String nome);

}
