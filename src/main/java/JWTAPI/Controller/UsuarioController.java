package JWTAPI.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import JWTAPI.DTO.BuscarDTO;
import JWTAPI.DTO.UsuarioDTO;
import JWTAPI.Exception.ValidationGroup.Login;
import JWTAPI.Service.Impl.MenuService;
import JWTAPI.Service.Impl.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.validation.annotation.Validated;


@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService service;

    @Autowired
    MenuService _menuService;
    
    @GetMapping("/ping")
    public String ping() {
        return "holaa sssssss pruebaaaa";
    }
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        return service.findAll();
    }

    @GetMapping("/findById/{usuarioId}")
    public ResponseEntity<?> findById(@RequestParam Integer usuarioId){
        return service.findById(usuarioId);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody UsuarioDTO dto){
        return service.Save(dto);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UsuarioDTO dto){
        return service.Update(dto);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam Integer usuarioId){
        return service.delete(usuarioId);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated({Login.class})@RequestBody UsuarioDTO dto){
        return service.Login(dto);
    }

    @PostMapping("/GetAccesoByIdUsuario")
    public ResponseEntity<?> GetAccesoByIdUsuario(@RequestBody UsuarioDTO dto){
        return _menuService.GetAccesoByIdUsuario(dto);
    }

    @PostMapping("/findUsuarioByNombreCuenta")
    public ResponseEntity<?> findUsuarioByNombreCuenta(@RequestBody BuscarDTO dto)
    {
         return service.findUsuarioByNombreCuenta(dto);
    }


}

