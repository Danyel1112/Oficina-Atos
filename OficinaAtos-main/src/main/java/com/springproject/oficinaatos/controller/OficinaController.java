package com.springproject.oficinaatos.controller;

import com.springproject.oficinaatos.model.Oficina;
import com.springproject.oficinaatos.repository.OficinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class OficinaController {

    @Autowired
    private OficinaRepository oficinaRepository;

    @ModelAttribute
    private void userDetails(Model m, Principal p) {
        String email = p.getName();
        Oficina oficina = oficinaRepository.findByEmail(email);
        m.addAttribute("user", oficina);
    }

    @GetMapping("/")
    public String home() {
        return "user/home/home";
    }


}