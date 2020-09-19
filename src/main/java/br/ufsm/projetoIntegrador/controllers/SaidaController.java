package br.ufsm.projetoIntegrador.controllers;

import br.ufsm.projetoIntegrador.comum.AcessoNegadoException;
import br.ufsm.projetoIntegrador.model.Dono;
import br.ufsm.projetoIntegrador.model.Funcionario;
import br.ufsm.projetoIntegrador.model.Saida;
import br.ufsm.projetoIntegrador.repository.DonoRepository;
import br.ufsm.projetoIntegrador.repository.EstacionamentoRepository;
import br.ufsm.projetoIntegrador.repository.FuncionarioRepository;
import br.ufsm.projetoIntegrador.repository.SaidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "saida")
public class SaidaController {

    @Autowired
    SaidaRepository saidaRepository;

    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Autowired
    DonoRepository donoRepository;

    @Autowired
    EstacionamentoRepository estacionamentoRepository;

    @GetMapping
    public List<Saida> list(Principal principal) {
        Dono dono = donoRepository.findByUsername(principal.getName());

        if (dono != null) {
            return saidaRepository.listSaidasByDono(dono.getId());
        } else {
            throw new AcessoNegadoException();
        }
    }

    @PostMapping
    public Saida create(@RequestBody Saida saida, Principal principal) {
        Funcionario func = funcionarioRepository.findByUsername(principal.getName());

        if (func != null) {
            saida.setFuncionario(func);
            saida.setHora(new Date());

            // calculo do tempo estacionado
            long tempo = saida.getHora().getTime() - saida.getEntrada().getHora().getTime();
            long tempoHoras = tempo / (60 * 60 * 1000) % 24;
            long tempoMinutos = tempo / (60 * 1000) % 60;

            // caso seja menos de 1hora
            if(tempoMinutos < 15 && tempoHoras == 0){
                saida.setValorPago((float) (0.25 * func.getEstacionamento().getValorPorHora()));
            } else if(tempoMinutos >= 15 && tempoMinutos <30 && tempoHoras == 0){
                saida.setValorPago((float) (0.5 * func.getEstacionamento().getValorPorHora()));
            } else if(tempoMinutos >= 30 && tempoMinutos <45 && tempoHoras == 0){
                saida.setValorPago((float) (0.75 * func.getEstacionamento().getValorPorHora()));
            } else{
                saida.setValorPago(tempoHoras * func.getEstacionamento().getValorPorHora());
            }
            Saida saidaSalva = saidaRepository.save(saida);
            func.getEstacionamento().setVagasDisponiveis(func.getEstacionamento().getVagasDisponiveis() + 1);
            estacionamentoRepository.save(func.getEstacionamento());
            return saidaSalva;

        } else {
            throw new AcessoNegadoException();
        }
    }
}
