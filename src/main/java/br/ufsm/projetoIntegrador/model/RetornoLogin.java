package br.ufsm.projetoIntegrador.model;

public class RetornoLogin {
    private Long id;
    private String nome;
    private String token;
    private String tipoUser;

    public RetornoLogin(Long id, String nome, String token, String tipoUser) {
        this.id = id;
        this.nome = nome;
        this.token = token;
        this.tipoUser = tipoUser;
    }

    public RetornoLogin() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipoUser() {
        return tipoUser;
    }

    public void setTipoUser(String tipoUser) {
        this.tipoUser = tipoUser;
    }
}
