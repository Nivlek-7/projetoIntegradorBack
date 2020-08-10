package br.ufsm.projetoIntegrador.controllers;

import br.ufsm.projetoIntegrador.comum.AcessoNegadoException;
import br.ufsm.projetoIntegrador.model.Dono;
import br.ufsm.projetoIntegrador.model.Funcionario;
import br.ufsm.projetoIntegrador.repository.DonoRepository;
import br.ufsm.projetoIntegrador.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "funcionario")
public class FuncionarioController {

    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Autowired
    DonoRepository donoRepository;

    @GetMapping
    public List<Funcionario> list(Principal principal){
        Dono dono = donoRepository.findByUsername(principal.getName());

        if (dono != null) {
            return funcionarioRepository.findFuncionariosByDono(dono.getId());
        } else {
            throw new AcessoNegadoException();
        }
    }

    @PostMapping
    public Funcionario create(@RequestBody Funcionario funcionario, Principal principal){
        Dono dono = donoRepository.findByUsername(principal.getName());

        if (dono != null) {
            if (funcionario.getEstacionamento().getDono().getId().equals(dono.getId())) {
                return funcionarioRepository.save(funcionario);
            } else {
                throw new AcessoNegadoException();
            }
        } else {
            throw new AcessoNegadoException();
        }
    }

    @PutMapping
    public Funcionario update(@RequestBody Funcionario funcionario, Principal principal){
        Dono dono = donoRepository.findByUsername(principal.getName());

        if (dono != null) {
            if (funcionario.getEstacionamento().getDono().getId().equals(dono.getId())) {
                return funcionarioRepository.save(funcionario);
            } else {
                throw new AcessoNegadoException();
            }
        } else {
            Funcionario func = funcionarioRepository.findByUsername(principal.getName());
            if (func != null && func.getId().equals(funcionario.getId())) {
                return funcionarioRepository.save(funcionario);
            } else {
                throw new AcessoNegadoException();
            }
        }
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id, Principal principal) {
        Dono dono = donoRepository.findByUsername(principal.getName());

        if (dono != null) {
            Optional<Funcionario> funcionario = funcionarioRepository.findById(id);

            if (funcionario.get().getEstacionamento().getDono().getId().equals(dono.getId())) {
                funcionarioRepository.deleteById(id);
            } else {
                throw new AcessoNegadoException();
            }
        } else {
            throw new AcessoNegadoException();
        }
    }
}
