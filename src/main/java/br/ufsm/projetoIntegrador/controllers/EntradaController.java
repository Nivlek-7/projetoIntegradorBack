package br.ufsm.projetoIntegrador.controllers;

import br.ufsm.projetoIntegrador.comum.AcessoNegadoException;
import br.ufsm.projetoIntegrador.model.Entrada;
import br.ufsm.projetoIntegrador.model.Funcionario;
import br.ufsm.projetoIntegrador.repository.EntradaRepository;
import br.ufsm.projetoIntegrador.repository.EstacionamentoRepository;
import br.ufsm.projetoIntegrador.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "entrada")
public class EntradaController {

    @Autowired
    EntradaRepository entradaRepository;

    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Autowired
    EstacionamentoRepository estacionamentoRepository;

    @GetMapping
    public List<Entrada> list(Principal principal) {
        Funcionario func = funcionarioRepository.findByUsername(principal.getName());

        if (func != null) {
            return entradaRepository.findEntradas(func.getEstacionamento().getId());
        } else {
            throw new AcessoNegadoException();
        }
    }

    @PostMapping
    public Entrada create(@RequestBody Entrada entrada, Principal principal) {
        Funcionario func = funcionarioRepository.findByUsername(principal.getName());

        if (func != null && func.getEstacionamento().getVagasDisponiveis() > 0) {
            entrada.setEstacionamento(func.getEstacionamento());
            entrada.setFuncionario(func);
            entrada.setHora(new Date());
            Entrada entradaSalva = entradaRepository.save(entrada);
            func.getEstacionamento().setVagasDisponiveis(func.getEstacionamento().getVagasDisponiveis() - 1);
            estacionamentoRepository.save(func.getEstacionamento());
            return entradaSalva;
        } else {
            throw new AcessoNegadoException();
        }
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id, Principal principal) {
        Funcionario func = funcionarioRepository.findByUsername(principal.getName());

        if (func != null) {
            Optional<Entrada> entrada = entradaRepository.findById(id);
            if (entrada.isPresent() && entrada.get().getEstacionamento().getId().equals(func.getEstacionamento().getId())) {
                entradaRepository.deleteById(id);
                func.getEstacionamento().setVagasDisponiveis(func.getEstacionamento().getVagasDisponiveis() + 1);
                estacionamentoRepository.save(func.getEstacionamento());
            } else {
                throw new AcessoNegadoException();
            }
        } else {
            throw new AcessoNegadoException();
        }
    }
}
