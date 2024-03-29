package com.gabru.Patrimonio.domain.services;

import com.gabru.Patrimonio.data.entities.Role;
import com.gabru.Patrimonio.data.entities.Usuario;
import com.gabru.Patrimonio.domain.exceptions.NotFoundException;
import com.gabru.Patrimonio.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String nombreUsuario) throws UsernameNotFoundException {

        Usuario usuario = userRepository
                .findByNombre(nombreUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado:  " + nombreUsuario));

        return this.userBuilder(usuario.getNombre(),usuario.getClave(),usuario.isActivo(),usuario.getRole());
    }

    private User userBuilder(String username, String clave, boolean activo, Role role){
        List<GrantedAuthority> authorities = new ArrayList<>();

            authorities.add(new SimpleGrantedAuthority(role.roleName()));

        return new User(username,clave,activo,true,true,true,authorities);
    }

    Usuario getUsuarioPorNombre(String nombreUsuario){ //Va a la BD
        Usuario usuario = userRepository.findByNombre(nombreUsuario)
                .orElseThrow(()-> new NotFoundException("Usuario: " + nombreUsuario + " no encontrado."));
        return usuario;
    }

    public Usuario getUserAuth (){
        return this.getUsuarioPorNombre(this.getNombreUsuarioAuthJWToken());
    }

    public String getNombreUsuarioAuthJWToken () {
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        return  nombreUsuario;
    }

}
