package br.ufsm.projetoIntegrador.repository;

import br.ufsm.projetoIntegrador.model.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EntradaRepository extends JpaRepository<Entrada, Long> {

    @Query("SELECT e FROM Estacionamento est, Entrada e WHERE est.id = e.estacionamento.id AND est.id = ?1 AND e.id NOT IN (SELECT s.entrada.id FROM Saida s) ")
    List<Entrada> findEntradas(Long id);
}

// SELECT entrada FROM estacionamento, entrada WHERE estacionamento.id = entrada.estacionamento_id AND
//	estacionamento.id = 1 and entrada.id not in (Select saida.entrada_id from saida);