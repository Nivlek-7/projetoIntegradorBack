package br.ufsm.projetoIntegrador.service;

import br.ufsm.projetoIntegrador.model.Dono;
import br.ufsm.projetoIntegrador.model.Funcionario;
import br.ufsm.projetoIntegrador.repository.DonoRepository;
import br.ufsm.projetoIntegrador.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private DonoRepository donoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Dono dono = donoRepository.findByUsername(username);
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (dono == null) {
            Funcionario func = funcionarioRepository.findByUsername(username);
            authorities.add(new SimpleGrantedAuthority("FUNC"));
            return new User(func.getUsername(), func.getSenha(), authorities);
        }
        authorities.add(new SimpleGrantedAuthority("DONO"));
        return new User(dono.getUsername(), dono.getSenha(), authorities);
    }
}
