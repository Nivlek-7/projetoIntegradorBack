package br.ufsm.projetoIntegrador.controllers;

import br.ufsm.projetoIntegrador.comum.AcessoNegadoException;
import br.ufsm.projetoIntegrador.model.Cliente;
import br.ufsm.projetoIntegrador.model.Funcionario;
import br.ufsm.projetoIntegrador.model.Veiculo;
import br.ufsm.projetoIntegrador.repository.ClienteRepository;
import br.ufsm.projetoIntegrador.repository.FuncionarioRepository;
import br.ufsm.projetoIntegrador.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "veiculo")
public class VeiculoController {

    @Autowired
    VeiculoRepository veiculoRepository;

    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping
    public List<Veiculo> list(Principal principal){
        Funcionario func = funcionarioRepository.findByUsername(principal.getName());

        if (func != null) {
            return veiculoRepository.findVeiculosByDonoID(func.getEstacionamento().getDono().getId());
        } else {
            throw new AcessoNegadoException();
        }
    }

    @GetMapping(value = "/listByCliente/{id}")
    public List<Veiculo> listByCliente(@PathVariable Long id, Principal principal){
        Funcionario func = funcionarioRepository.findByUsername(principal.getName());

        if (func != null) {
            Optional<Cliente> cliente = clienteRepository.findById(id);
            if (cliente.isPresent() && func.getEstacionamento().getDono().getId().equals(cliente.get().getDono().getId())){
                return veiculoRepository.findByCliente(cliente.get());
            } else {
                throw new AcessoNegadoException();
            }
        } else {
            throw new AcessoNegadoException();
        }
    }

    @PostMapping
    public Veiculo create(@RequestBody Veiculo veiculo, Principal principal){
        Funcionario func = funcionarioRepository.findByUsername(principal.getName());

        if (func != null) {
            return veiculoRepository.save(veiculo);
        } else {
            throw new AcessoNegadoException();
        }
    }

    @PutMapping
    public Veiculo update(@RequestBody Veiculo veiculo, Principal principal){
        Funcionario func = funcionarioRepository.findByUsername(principal.getName());

        if (func != null && func.getEstacionamento().getDono().getId().equals(veiculo.getCliente().getDono().getId())) {
            return veiculoRepository.save(veiculo);
        } else {
            throw new AcessoNegadoException();
        }
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id, Principal principal){
        Funcionario func = funcionarioRepository.findByUsername(principal.getName());

        if (func != null) {
            Optional<Veiculo> veiculo = veiculoRepository.findById(id);
            if (veiculo.isPresent() && func.getEstacionamento().getDono().getId().equals(veiculo.get().getCliente().getDono().getId())) {
                veiculoRepository.deleteById(id);
            } else {
                throw new AcessoNegadoException();
            }
        } else {
            throw new AcessoNegadoException();
        }
    }
}
