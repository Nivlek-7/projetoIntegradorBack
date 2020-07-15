package br.ufsm.projetoIntegrador.comum;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AcessoNegadoException extends RuntimeException{

    @Override
    public String getMessage() {
        return "Acesso negado";
    }
}
