package JWTAPI.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import JWTAPI.DTO.SeguridadDTO;
import JWTAPI.Service.SeguridadService;

@RestController
@RequestMapping("/api/seguridad")
public class SeguridadController {
    @Autowired
    SeguridadService seguridadService;

    @PostMapping("/LoginUsuario")
    public ResponseEntity<?> LoginUsuario(@RequestBody SeguridadDTO seguridadDTO){
        return seguridadService.LoginUsuario(seguridadDTO);
    }

    @PostMapping("/RegistrarUsuario")
    public ResponseEntity<?> RegistrarUsuario(@RequestBody SeguridadDTO seguridadDTO){
        return seguridadService.RegistrarUsuario(seguridadDTO);
    }


    
}
