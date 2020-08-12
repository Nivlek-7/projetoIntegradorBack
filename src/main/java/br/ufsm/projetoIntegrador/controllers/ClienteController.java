package br.ufsm.projetoIntegrador.controllers;

import br.ufsm.projetoIntegrador.comum.AcessoNegadoException;
import br.ufsm.projetoIntegrador.model.Cliente;
import br.ufsm.projetoIntegrador.model.Funcionario;
import br.ufsm.projetoIntegrador.repository.ClienteRepository;
import br.ufsm.projetoIntegrador.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "cliente")
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    FuncionarioRepository funcionarioRepository;

    @GetMapping
    public List<Cliente> list(Principal principal){
        Funcionario func = funcionarioRepository.findByUsername(principal.getName());

        if (func != null) {
            return clienteRepository.findAllByDono(func.getEstacionamento().getDono());
        } else {
            throw new AcessoNegadoException();
        }
    }

    @PostMapping
    public Cliente create(@RequestBody Cliente cliente, Principal principal){
        Funcionario func = funcionarioRepository.findByUsername(principal.getName());

        if (func != null) {
            cliente.setDono(func.getEstacionamento().getDono());
            return clienteRepository.save(cliente);
        } else {
            throw new AcessoNegadoException();
        }
    }

    @PutMapping
    public Cliente update(@RequestBody Cliente cliente, Principal principal){
        Funcionario func = funcionarioRepository.findByUsername(principal.getName());

        if (func != null && func.getEstacionamento().getDono().getId().equals(cliente.getDono().getId())) {
            return clienteRepository.save(cliente);
        } else {
            throw new AcessoNegadoException();
        }
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id, Principal principal) {
        Funcionario func = funcionarioRepository.findByUsername(principal.getName());

        if (func != null) {
            Optional<Cliente> cliente = clienteRepository.findById(id);
            if (cliente.get().getDono().getId().equals(func.getEstacionamento().getDono().getId())) {
                clienteRepository.deleteById(id);
            } else {
                throw new AcessoNegadoException();
            }
        } else {
            throw new AcessoNegadoException();
        }
    }
}
