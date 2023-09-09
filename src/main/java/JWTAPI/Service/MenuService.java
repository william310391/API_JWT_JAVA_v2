package JWTAPI.Service;

import org.springframework.http.ResponseEntity;

import JWTAPI.DTO.UsuarioDTO;

public interface MenuService {
    public ResponseEntity<?> GetAccesoByIdUsuario(UsuarioDTO dto);
}
