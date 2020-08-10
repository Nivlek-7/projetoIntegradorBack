package br.ufsm.projetoIntegrador.repository;

import br.ufsm.projetoIntegrador.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    @Query("SELECT f FROM Dono d, Estacionamento e, Funcionario f WHERE d.id = ?1 AND d.id = e.dono.id AND e.id = f.estacionamento.id ORDER BY f.id")
    List<Funcionario> findFuncionariosByDono(Long id);

    Funcionario findByUsername(String username);
}
