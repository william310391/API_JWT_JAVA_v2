package JWTAPI.Service;

import org.springframework.http.ResponseEntity;

import JWTAPI.DTO.SeguridadDTO;

public interface SeguridadService {
    public ResponseEntity<?> LoginUsuario(SeguridadDTO seguridad);
}
