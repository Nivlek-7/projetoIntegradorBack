package br.ufsm.projetoIntegrador.controllers;

import br.ufsm.projetoIntegrador.dto.AuthRequest;
import br.ufsm.projetoIntegrador.model.Dono;
import br.ufsm.projetoIntegrador.dto.RetornoLogin;
import br.ufsm.projetoIntegrador.repository.DonoRepository;
import br.ufsm.projetoIntegrador.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DonoRepository donoRepository;

    @PostMapping(value = "authenticate")
    public RetornoLogin generateToken(@RequestBody AuthRequest authRequest) throws Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getSenha())
            );
        } catch (Exception e){
            throw new Exception("Usuário/senha inválido");
        }

        Dono dono = donoRepository.findByUsername(authRequest.getUsername());

        if (dono != null) {
            return new RetornoLogin(dono.getId(), dono.getNome(), jwtUtil.generateToken(dono.getUsername()), "dono");
        }  else {
            // procura funcionario
            return new RetornoLogin();
        }

    }

}
