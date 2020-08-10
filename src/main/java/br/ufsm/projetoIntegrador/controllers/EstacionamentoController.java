package br.ufsm.projetoIntegrador.controllers;

import br.ufsm.projetoIntegrador.comum.AcessoNegadoException;
import br.ufsm.projetoIntegrador.model.Dono;
import br.ufsm.projetoIntegrador.model.Estacionamento;
import br.ufsm.projetoIntegrador.repository.DonoRepository;
import br.ufsm.projetoIntegrador.repository.EstacionamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "estacionamento")
public class EstacionamentoController {

    @Autowired
    EstacionamentoRepository estacionamentoRepository;

    @Autowired
    DonoRepository donoRepository;

    @GetMapping
    public List<Estacionamento> list(Principal principal){
        Dono dono = donoRepository.findByUsername(principal.getName());

        if (dono != null) {
            return estacionamentoRepository.findAllByDono(dono);
        } else {
            throw new AcessoNegadoException();
        }
    }

    @PostMapping
    public Estacionamento create(@RequestBody Estacionamento estacionamento, Principal principal){
        Dono dono = donoRepository.findByUsername(principal.getName());

        if (dono != null) {
            estacionamento.setDono(dono);
            return estacionamentoRepository.save(estacionamento);
        } else {
            throw new AcessoNegadoException();
        }
    }

    @PutMapping
    public Estacionamento update(@RequestBody Estacionamento estacionamento, Principal principal){
        Dono dono = donoRepository.findByUsername(principal.getName());

        if (dono != null) {
            if (estacionamento.getDono().getId().equals(dono.getId())) {
                return estacionamentoRepository.save(estacionamento);
            } else {
                throw new AcessoNegadoException();
            }
        } else {
            throw new AcessoNegadoException();
        }
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable(value = "id") Long id, Principal principal){
        Dono dono = donoRepository.findByUsername(principal.getName());
        Optional<Estacionamento> e = estacionamentoRepository.findById(id);

        if (e.isPresent()){
            if (e.get().getDono().getId().equals(dono.getId())) {
                estacionamentoRepository.deleteById(id);
            } else {
                throw new AcessoNegadoException();
            }
        }
    }

}
