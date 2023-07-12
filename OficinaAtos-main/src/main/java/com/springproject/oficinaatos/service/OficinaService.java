package com.springproject.oficinaatos.service;

import com.springproject.oficinaatos.model.Oficina;

public interface OficinaService {

    public Oficina createUser(Oficina oficina);

    public boolean checkEmail(String email);

}
