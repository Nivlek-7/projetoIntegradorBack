package br.ufsm.projetoIntegrador.repository;

import br.ufsm.projetoIntegrador.model.Cliente;
import br.ufsm.projetoIntegrador.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    @Query("SELECT v FROM Veiculo v, Cliente c, Dono d WHERE v.cliente.id = c.id AND c.dono.id = ?1 AND d.id = ?1")
    List<Veiculo> findVeiculosByDonoID(Long id);

    List<Veiculo> findByCliente(Cliente cliente);
}

//select v.id, v.cor, v.modelo, v.placa, v.cliente_id from veiculo v, cliente c, dono d
//    where v.cliente_id = c.id and c.dono_id = 1 and d.id = 1;