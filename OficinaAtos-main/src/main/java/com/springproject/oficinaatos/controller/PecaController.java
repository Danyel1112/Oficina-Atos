package com.springproject.oficinaatos.controller;

import com.springproject.oficinaatos.model.Peca;
import com.springproject.oficinaatos.service.PecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pecas")
public class PecaController {
    private final PecaService pecaService;

    @Autowired
    public PecaController(PecaService pecaService) {
        this.pecaService = pecaService;
    }

    @GetMapping
    public String listarPecas(Model model) {
        List<Peca> pecas = pecaService.listarPecas();
        model.addAttribute("pecas", pecas);
        return "user/pecas/home";
    }

    @GetMapping("/form")
    public String mostrarFormulario(Model model) {
        model.addAttribute("peca", new Peca());
        return "user/pecas/pecaform";
    }

    @PostMapping("/cadastrar")
    public String cadastrarPeca(@ModelAttribute("peca") Peca peca) {
        pecaService.cadastrarPeca(peca);
        return "redirect:/pecas";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable("id") Long id, Model model) {
        Peca peca = pecaService.buscarPecaPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Peça inválida com ID: " + id));
        List<Peca> pecas = pecaService.listarPecas();
        model.addAttribute("peca", peca);
        model.addAttribute("pecas", pecas);
        return "user/pecas/pecaform";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarPeca(@PathVariable("id") Long id, @ModelAttribute("peca") Peca pecaAtualizada) {
        pecaService.editarPeca(id, pecaAtualizada);
        return "redirect:/pecas";
    }

    @GetMapping("/excluir/{id}")
    public String excluirPeca(@PathVariable("id") Long id) {
        pecaService.excluirPeca(id);
        return "redirect:/pecas";
    }

    @GetMapping("/contatos")
    public String exibirContatos() {
        return "user/pecas/contatos";
    }

    @GetMapping("/ajuda")
    public String exibirAjuda(){
        return "user/pecas/ajuda";
    }


    @GetMapping("/peca/buscar")
    public String buscarPecaPorNome(@RequestParam("nome") String nome, Model model) {
        List<Peca> pecas = pecaService.buscarPecaPorNome(nome);
        model.addAttribute("pecas", pecas);
        return "user/pecas/home";
    }
}
