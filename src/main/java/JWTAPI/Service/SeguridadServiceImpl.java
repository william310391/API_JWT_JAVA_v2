package JWTAPI.Service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import JWTAPI.DTO.ApiResponse;
import JWTAPI.DTO.SeguridadDTO;
import JWTAPI.Entity.Seguridad;
import JWTAPI.Repository.SeguridadRepository;
import JWTAPI.Security.TokenUtils;

@Service
public class SeguridadServiceImpl implements SeguridadService {

    @Autowired
    private SeguridadRepository seguridadRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;

    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    public ResponseEntity<?> LoginUsuario(SeguridadDTO seguridadDTO) {
        ApiResponse<SeguridadDTO> response = new ApiResponse<SeguridadDTO>(null);

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(seguridadDTO.getUsuario(), seguridadDTO.getContrasena()));
            if (auth.isAuthenticated()) {
                logger.info("Logged In");
                UserDetails userDetails = userDetailsService.loadUserByUsername(seguridadDTO.getUsuario());
                String token = TokenUtils.generateToken(userDetails);
                SeguridadDTO dto = new SeguridadDTO();
                Seguridad datosUsuario= seguridadRepository.findSeguridadByUsuario(userDetails.getUsername());
                dto.setNombre(datosUsuario.getNombre());
                dto.setUsuario(datosUsuario.getUsuario());
                dto.setToken(token);
                response.setData(dto);
                response.setCodigoHTTP(200);
                response.setResultadoDescripcion("Logged In");
                response.setResultadoIndicador(1);
            }

        } catch (DisabledException e) {

            response.setResultadoDescripcion("User is disabled");
            response.setCodigoHTTP(500);
            e.printStackTrace();

        } catch (BadCredentialsException e) {

            response.setResultadoDescripcion("Invalid Credentials");
            response.setCodigoHTTP(401);
            e.printStackTrace();

        } catch (Exception e) {
            response.setResultadoDescripcion("Something went wrong");
            response.setCodigoHTTP(500);
            e.printStackTrace();
        }

        return ResponseEntity.status(response.getCodigoHTTP()).body(response);
    }

}
