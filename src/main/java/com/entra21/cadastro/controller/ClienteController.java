package com.entra21.cadastro.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.entra21.cadastro.model.Cliente;
import com.entra21.cadastro.model.ClienteRepository;

@Controller
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping("/clientes")
    public String clientes(Model model){
        model.addAttribute("listaClientes", clienteRepository.findAll());
        return "index";
    }
    @GetMapping("/clientes/novo")
    public String novoCliente(@ModelAttribute("cliente") Cliente cliente){
        return "form";
    }

    @PostMapping("/clientes/salvar")
    public String salvaCliente(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult resultado){
        if(resultado.hasErrors()){
            return "/form";
        }
        clienteRepository.save(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/clientes/{id}")
    public String editaCliente(@PathVariable("id") long id, Model model){
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);
            if(clienteOpt.isEmpty()){
                throw new IllegalArgumentException("Usuário Inválido"); 
            }
            model.addAttribute("cliente", clienteOpt.get());
            return "/form";
    }

    @GetMapping("/clientes/deletar/{id}")
    public String deletaCliente(@PathVariable("id") long id){
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);
            if (clienteOpt.isEmpty()){
                throw new IllegalArgumentException("Usuário Inválido");
            }
            clienteRepository.delete(clienteOpt.get());
            return "redirect:/clientes";

    }
    
}
