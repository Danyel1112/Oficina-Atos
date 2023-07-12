package com.springproject.oficinaatos.controller;

import com.springproject.oficinaatos.model.Oficina;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.springproject.oficinaatos.service.OficinaService;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("session")
public class HomeController {

    @Autowired
    private OficinaService oficinaService;

    @GetMapping("/")
    public String paginaInicial() {
        return "index";
    }

    @GetMapping
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registro(@ModelAttribute HttpServletRequest httpServletRequest) {
        return "register";
    }

    @PostMapping("/logout")
    public String logout() {
        System.out.println("Logout");
        return "redirect:/login";
    }

    @PostMapping("/createUser")
    public String criarUsuario(@ModelAttribute Oficina usuario, HttpSession session) {

        boolean emailExiste = oficinaService.checkEmail(usuario.getEmail());

        if (emailExiste) {
            session.setAttribute("msg", "E-mail já existe!");
        } else {
            Oficina detalhesUsuario = oficinaService.createUser(usuario);
            if (detalhesUsuario != null) {
                session.setAttribute("msg", "Registrado com sucesso.");
            } else {
                session.setAttribute("msg", "Algo errado com o servidor.");
            }
        }

        return "redirect:/register";
    }

    @GetMapping("/login")
    public String mostrarPaginaLogin(HttpServletRequest request, Model model) {
        AuthenticationException exception =
                (AuthenticationException) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (exception != null) {
            model.addAttribute("error", "Usuário ou senha inválidos.");
            request.getSession().removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
        return "login";
    }
}
