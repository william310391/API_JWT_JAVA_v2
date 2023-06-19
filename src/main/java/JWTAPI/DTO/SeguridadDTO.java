package JWTAPI.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeguridadDTO {
    int id;
	String nombre;
	String usuario;
	String contrasena;
	String correo;
    String token;
}
