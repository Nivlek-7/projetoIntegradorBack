package br.ufsm.projetoIntegrador.repository;

import br.ufsm.projetoIntegrador.model.Dono;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonoRepository extends JpaRepository<Dono, Long> {
    Dono findByUsername(String username);

}
