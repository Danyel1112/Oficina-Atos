package com.springproject.oficinaatos.repository;

import com.springproject.oficinaatos.model.Oficina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OficinaRepository extends JpaRepository<Oficina, Integer> {

    public boolean existsByEmail(String email);
    public Oficina findByEmail(String email);

}
