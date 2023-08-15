package JWTAPI.Service;

import org.springframework.http.ResponseEntity;

import JWTAPI.DTO.BuscarDTO;
import JWTAPI.DTO.UsuarioDTO;

public interface UsuarioService {
    public ResponseEntity<?> findAll();
    public ResponseEntity<?> findById(Integer UsuarioId);
    public ResponseEntity<?> findUsuarioByNombreCuenta(BuscarDTO buscarDTO);
    public ResponseEntity<?> Save(UsuarioDTO usuarioDTO);
    public ResponseEntity<?> Update(UsuarioDTO usuarioDTO);
    public ResponseEntity<?> delete(Integer usuarioId);
    public ResponseEntity<?> Login(UsuarioDTO usuarioDTO);}

