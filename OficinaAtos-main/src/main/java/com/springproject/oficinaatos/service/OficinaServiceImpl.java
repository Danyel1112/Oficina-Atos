package com.springproject.oficinaatos.service;

import com.springproject.oficinaatos.model.Oficina;
import com.springproject.oficinaatos.repository.OficinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OficinaServiceImpl implements OficinaService {

    @Autowired
    private OficinaRepository oficinaRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncode;

    @Override
    public Oficina createUser(Oficina oficina) {

        oficina.setPassword(passwordEncode.encode(oficina.getPassword()));
        oficina.setRole("ROLE_USER");

        return oficinaRepository.save(oficina);
    }

    @Override
    public boolean checkEmail(String email) {

        return oficinaRepository.existsByEmail(email);
    }

}
