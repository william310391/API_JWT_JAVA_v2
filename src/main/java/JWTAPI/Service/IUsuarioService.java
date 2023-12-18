package JWTAPI.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import JWTAPI.DTO.ApiResponse;
import JWTAPI.DTO.BuscarDTO;
import JWTAPI.DTO.UsuarioDTO;
import JWTAPI.DTO.MapperConfig.UsuarioMapper;
import JWTAPI.Entity.Usuario;
import JWTAPI.Exception.BusinessException;
import JWTAPI.Repository.UsuarioRepository;
import JWTAPI.Service.Impl.UsuarioService;

@Service
public class IUsuarioService implements UsuarioService {

    @Autowired
    private UsuarioRepository repo;  

    public ResponseEntity<?> findAll() {
        List<Usuario> result = repo.findAll();
        List<UsuarioDTO> dto = UsuarioMapper.INSTANCE.getUsuarios(result);
        ApiResponse<List<UsuarioDTO>> response = new ApiResponse<List<UsuarioDTO>>(dto);        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<?> findUsuarioByNombreCuenta(BuscarDTO buscarDTO) {
        List<Usuario> result = repo.findUsuarioByNombreCuenta(buscarDTO.getBuscar());
        List<UsuarioDTO> dto = UsuarioMapper.INSTANCE.getUsuarios(result);
        ApiResponse<List<UsuarioDTO>> response = new ApiResponse<List<UsuarioDTO>>(dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<?> findById(Integer UsuarioId) {
        try {
            Optional<Usuario> usuario = repo.findById(UsuarioId);
            UsuarioDTO usuarioDTO = UsuarioMapper.INSTANCE.usuarioToUsuarioDTO(usuario.get());
            ApiResponse<UsuarioDTO> response = new ApiResponse<UsuarioDTO>(usuarioDTO);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public ResponseEntity<?> Save(UsuarioDTO usuarioDTO) {
        try {
            Usuario entVal = repo.findUsuarioByCuenta(usuarioDTO.getCuenta());
            if (!Objects.isNull(entVal)) {
                throw new BusinessException(HttpStatus.BAD_REQUEST, "la cuenta se encuentra registrada");
            }
            Usuario ent = UsuarioMapper.INSTANCE.usuarioDTOToUsuario(usuarioDTO);
            ent = repo.save(ent);
            ApiResponse<UsuarioDTO> response = new ApiResponse<UsuarioDTO>(usuarioDTO);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    public ResponseEntity<?> Update(UsuarioDTO usuarioDTO) {

        try {
            if (!repo.existsById(usuarioDTO.getId())) {
                throw new BusinessException(HttpStatus.BAD_REQUEST, "El usuario ingresado no existe");
            }
            Usuario ent = UsuarioMapper.INSTANCE.usuarioDTOToUsuario(usuarioDTO);
            ent = repo.save(ent);
            ApiResponse<Usuario> response = new ApiResponse<Usuario>(ent);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public ResponseEntity<?> delete(Integer UsuarioId) {
        try {
            if (!repo.existsById(UsuarioId)) {
                throw new BusinessException(HttpStatus.BAD_REQUEST, "El usuario no existe");
            }   
            repo.deleteById(UsuarioId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<String>("Registro Eliminado"));

        } catch (Exception e) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public ResponseEntity<?> Login(UsuarioDTO usuarioDTO) {
        try {
            Usuario entVal = repo.findUsuarioByCuenta(usuarioDTO.getCuenta());
            if (Objects.isNull(entVal)) {
                throw new BusinessException(HttpStatus.BAD_REQUEST, "cuenta no valida");
            }
            if(!new BCryptPasswordEncoder().matches(usuarioDTO.getContrasena(),entVal.getContrasena())) {
                throw new BusinessException(HttpStatus.BAD_REQUEST, "cuenta o contraseña no valida");
            }          
            usuarioDTO=UsuarioMapper.INSTANCE.usuarioToUsuarioDTO(entVal);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<UsuarioDTO>(usuarioDTO));            
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }




}
