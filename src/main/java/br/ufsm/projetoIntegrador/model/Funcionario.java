package br.ufsm.projetoIntegrador.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Funcionario {
    @Getter
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cpf;
    @Column(nullable = false)
    private String endereco;
    @Column(nullable = false)
    private String telefone;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String senha;

    @ManyToOne
    @JoinColumn(name = "estacionamento_id", nullable = false)
    private Estacionamento estacionamento;
}
