package com.springproject.oficinaatos.config;

import com.springproject.oficinaatos.model.Oficina;
import com.springproject.oficinaatos.repository.OficinaRepository;
import com.springproject.oficinaatos.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OficinaDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private OficinaRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Oficina oficina = userRepo.findByEmail(email);

        if (oficina != null) {
            return new CustomUserDetails(oficina);
        }
        throw new UsernameNotFoundException("Usuário Inválido");
    }
}
