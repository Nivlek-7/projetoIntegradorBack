package br.ufsm.projetoIntegrador.controllers;

import br.ufsm.projetoIntegrador.dto.AuthRequest;
import br.ufsm.projetoIntegrador.model.Dono;
import br.ufsm.projetoIntegrador.dto.RetornoLogin;
import br.ufsm.projetoIntegrador.model.Funcionario;
import br.ufsm.projetoIntegrador.repository.DonoRepository;
import br.ufsm.projetoIntegrador.repository.FuncionarioRepository;
import br.ufsm.projetoIntegrador.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @PostMapping(value = "authenticate")
    public RetornoLogin generateToken(@RequestBody AuthRequest authRequest) throws Exception{
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getSenha()));
        } catch (Exception e){
            throw new Exception("Usuário/senha inválido");
        }

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("DONO"))) {
            Dono dono = donoRepository.findByUsername(authRequest.getUsername());
            return new RetornoLogin(dono.getId(), dono.getNome(), jwtUtil.generateToken(dono.getUsername()), "dono");
        } else {
            Funcionario func = funcionarioRepository.findByUsername(authRequest.getUsername());
            return new RetornoLogin(func.getId(), func.getNome(), jwtUtil.generateToken(func.getUsername()), "funcionario");
        }

    }

}
