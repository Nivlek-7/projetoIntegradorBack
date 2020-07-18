package br.ufsm.projetoIntegrador.repository;

import br.ufsm.projetoIntegrador.model.Dono;
import br.ufsm.projetoIntegrador.model.Estacionamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstacionamentoRepository extends JpaRepository<Estacionamento, Long> {

    List<Estacionamento> findAllByDono(Dono dono);
}
