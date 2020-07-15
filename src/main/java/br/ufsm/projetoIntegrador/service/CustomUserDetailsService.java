package br.ufsm.projetoIntegrador.service;

import br.ufsm.projetoIntegrador.model.Dono;
import br.ufsm.projetoIntegrador.repository.DonoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private DonoRepository donoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Dono dono = donoRepository.findByUsername(username);
        return new User(dono.getUsername(), dono.getSenha(), new ArrayList<>());
    }
}
