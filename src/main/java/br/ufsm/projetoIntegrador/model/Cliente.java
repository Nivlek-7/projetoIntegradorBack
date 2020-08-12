package br.ufsm.projetoIntegrador.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class Cliente implements Serializable {
    @Getter
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String telefone;
    @Column(nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "dono_id", nullable = false)
    private Dono dono;
}
