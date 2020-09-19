package br.ufsm.projetoIntegrador.repository;

import br.ufsm.projetoIntegrador.model.Saida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaidaRepository extends JpaRepository<Saida, Long> {

    @Query("SELECT s FROM Saida s, Entrada ent, Estacionamento est, Dono d WHERE ent.id = s.entrada.id AND ent.estacionamento.id = est.id " +
            "AND est.dono.id = d.id AND d.id = ?1")
    List<Saida> listSaidasByDono(Long id);
}


// SELECT s.id, s.hora, s.valor_pago, s.entrada_id, s.funcionario_id FROM saida s, entrada ent, estacionamento est, dono d
//WHERE ent.id = s.entrada_id and ent.estacionamento_id = est.id and est.dono_id = d.id and d.id = 2