package JWTAPI.Service;

import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import JWTAPI.DTO.ApiResponse;
import JWTAPI.DTO.SeguridadDTO;
import JWTAPI.DTO.MapperConfig.SeguridadMapper;
import JWTAPI.Entity.Seguridad;
import JWTAPI.Exception.BusinessException;
import JWTAPI.Repository.SeguridadRepository;
import JWTAPI.Security.TokenUtils;
import JWTAPI.Security.UserDetailsServiceImpl;
import JWTAPI.Service.Impl.SeguridadService;

@Service
public class ISeguridadService implements SeguridadService {

    @Autowired
    private SeguridadRepository seguridadRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    public ResponseEntity<?> LoginUsuario(SeguridadDTO seguridadDTO) {
        ApiResponse<SeguridadDTO> response = new ApiResponse<SeguridadDTO>(null);

        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(seguridadDTO.getUsuario(), seguridadDTO.getContrasena()));
            
            if (auth.isAuthenticated()) {
                logger.info("Logged In");
                UserDetails userDetails = userDetailsService.loadUserByUsername(seguridadDTO.getUsuario());
                String token = TokenUtils.generateToken(userDetails);
                Seguridad datosUsuario= seguridadRepository.findSeguridadByUsuario(userDetails.getUsername());
                SeguridadDTO dto = SeguridadMapper.INSTANCE.seguridadToSeguridadDTO(datosUsuario);
                dto.setToken(token);
                response.setData(dto);
                response.setCodigoHTTP(200);
                response.setResultadoDescripcion("Logged In");
                response.setResultadoIndicador(true);
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

    @Override
    public ResponseEntity<?> RegistrarUsuario(SeguridadDTO seguridadDTO) {

        if (!Objects.isNull(seguridadRepository.findSeguridadByUsuario(seguridadDTO.getUsuario()))){
            throw new BusinessException(HttpStatus.BAD_REQUEST, "El usuario se encuentra registrado");
        }
        logger.info("Register");
        ApiResponse<SeguridadDTO> response = new ApiResponse<SeguridadDTO>(null);

        Seguridad seguridad = SeguridadMapper.INSTANCE.seguridadDTOToSeguridad(seguridadDTO);
        seguridad.setContrasena(new BCryptPasswordEncoder().encode(seguridadDTO.getContrasena()));
        seguridadRepository.save(seguridad);

        UserDetails userDetails = userDetailsService.createUserDetails(seguridadDTO.getUsuario(), seguridadDTO.getContrasena());

        seguridadDTO = SeguridadMapper.INSTANCE.seguridadToSeguridadDTO(seguridad);
        seguridadDTO.setToken(TokenUtils.generateToken(userDetails));
        response.setData(seguridadDTO);

        return ResponseEntity.ok(response);
    }
}
