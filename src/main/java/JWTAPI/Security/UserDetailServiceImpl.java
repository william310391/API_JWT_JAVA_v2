package JWTAPI.Security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import JWTAPI.Entity.Seguridad;
import JWTAPI.Repository.SeguridadRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private SeguridadRepository seguridadRepository;

    @Override
    public UserDetails loadUserByUsername(String cuenta) throws UsernameNotFoundException {

        Seguridad seguridad = seguridadRepository.findSeguridadByUsuario(cuenta);     
        return new UserDetailsImpl(seguridad);

    }

}
