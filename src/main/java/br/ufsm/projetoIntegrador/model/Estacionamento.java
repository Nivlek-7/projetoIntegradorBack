package br.ufsm.projetoIntegrador.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
public class Estacionamento implements Serializable {
    @Getter
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String endereco;
    @Column(nullable = false)
    private String telefone;
    @Column(nullable = false)
    private float valorPorHora;
    @Column(nullable = false)
    private int vagas;
    @Column(nullable = false)
    private int vagasDisponiveis;

    @ManyToOne
    @JoinColumn(name = "dono_id", nullable = false)
    private Dono dono;

    @JsonIgnore
    @OneToMany(mappedBy = "estacionamento", cascade = CascadeType.ALL)
    private List<Funcionario> funcionarios;

    @JsonIgnore
    @OneToMany(mappedBy = "estacionamento", cascade = CascadeType.ALL)
    private List<Entrada> entradas;
}
