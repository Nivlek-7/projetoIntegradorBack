package br.ufsm.projetoIntegrador.controllers;

import br.ufsm.projetoIntegrador.comum.AcessoNegadoException;
import br.ufsm.projetoIntegrador.model.Dono;
import br.ufsm.projetoIntegrador.repository.DonoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "dono")
public class DonoController {

    @Autowired
    DonoRepository donoRepository;

    @GetMapping(value = "/listar")
    public Dono listaDono(Principal principal){
        return donoRepository.findByUsername(principal.getName());
    }

    @PostMapping
    public Dono cadastrarDono(@RequestBody Dono dono){
        return donoRepository.save(dono);
    }

    @PutMapping
    public Dono editarDono(@RequestBody Dono dono, Principal principal){
        Dono donoxd = donoRepository.findByUsername(principal.getName());
        if(!donoxd.getId().equals(dono.getId()))
            throw new AcessoNegadoException();

        return donoRepository.save(dono);
    }

    @DeleteMapping(value = "/{id}")
    public void deletaDono(@PathVariable(value = "id") Long id, Principal principal){
        Dono dono = donoRepository.findByUsername(principal.getName());
        if(!dono.getId().equals(id))
            throw new AcessoNegadoException();

        donoRepository.deleteById(id);
    }

}
