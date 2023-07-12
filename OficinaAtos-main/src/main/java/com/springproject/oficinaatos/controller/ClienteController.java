package com.springproject.oficinaatos.controller;

import com.springproject.oficinaatos.model.Cliente;
import com.springproject.oficinaatos.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/cliente/home")
    public String mostrarClientesCadastrados(Model model) {
        List<Cliente> clientes = clienteService.listarClientes();
        model.addAttribute("clientes", clientes);
        return "user/cliente/home";
    }

    @GetMapping("/cliente/formulario")
    public String exibirFormulario(Model model) {
        Cliente cliente = new Cliente();
        model.addAttribute("cliente", cliente);
        return "user/cliente/formulario";
    }

    @PostMapping("/cliente/formulario")
    public String cadastrarCliente(
            @ModelAttribute("cliente") @Validated Cliente cliente,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "user/cliente/formulario";
        }

        clienteService.cadastrarCliente(cliente);
        return "redirect:/cliente/home";
    }

    @GetMapping("/cliente/formulario/{id}")
    public String mostrarFormularioEdicao(@PathVariable("id") long id, Model model) {
        Optional<Cliente> cliente = clienteService.buscarClientePorId(id);
        if (cliente.isPresent()) {
            model.addAttribute("cliente", cliente.get());
            return "user/cliente/formulario";
        } else {
            return "redirect:/home";
        }
    }

    @PostMapping("/cliente/formulario/{id}")
    public String editarCliente(@PathVariable("id") long id, @ModelAttribute("cliente") @Validated Cliente cliente, BindingResult result) {
        if (result.hasErrors()) {
            return "cliente/formulario";
        }
        cliente.setId(id);
        clienteService.cadastrarCliente(cliente);
        return "redirect:/home";
    }

    @GetMapping("/cliente/excluir/{id}")
    public String excluirCliente(@PathVariable("id") long id) {
        clienteService.excluirCliente(id);
        return "redirect:/cliente/home";
    }

    @GetMapping("/cliente/ajuda")
    public String mostrarPaginaAjuda() {
        return "user/cliente/ajuda";
    }

    @GetMapping("/cliente/contatos")
    public String mostrarPaginaContato() {
        return "user/cliente/contatos";
    }

    @GetMapping("/cliente/pdf/{id}")
    public ResponseEntity<byte[]> gerarClientePDF(@PathVariable("id") long id) {
        Optional<Cliente> cliente = clienteService.buscarClientePorId(id);
        if (cliente.isPresent()) {
            return PDFController.generateClientePDF(cliente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cliente/buscar")
    public String buscarClientesPorNome(@RequestParam("nome") String nome, Model model) {
        List<Cliente> clientes = clienteService.buscarClientesPorNome(nome);
        model.addAttribute("clientes", clientes);
        return "user/cliente/home";
    }
}
