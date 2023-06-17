package JWTAPI.Security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import JWTAPI.Entity.Usuario;
import JWTAPI.Repository.UsuarioRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String cuenta) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findUsuarioByCuenta(cuenta);     
        return new UserDetailsImpl(usuario);

    }

}
