package br.ufsm.projetoIntegrador.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class Veiculo implements Serializable {
    @Getter
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String placa;
    @Column(nullable = false)
    private String modelo;
    @Column(nullable = false)
    private String cor;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
