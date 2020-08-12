package br.ufsm.projetoIntegrador.repository;

import br.ufsm.projetoIntegrador.model.Cliente;
import br.ufsm.projetoIntegrador.model.Dono;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findAllByDono(Dono dono);
}
