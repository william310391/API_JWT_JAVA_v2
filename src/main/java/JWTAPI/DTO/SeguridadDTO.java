package JWTAPI.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import JWTAPI.Exception.ValidationGroup.Login;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeguridadDTO {
    int id;
	String nombre;
	@NotBlank(message = "campo usuario no puede estar en blanco",groups = Login.class)
	@NotNull(message = "campo requerido usuario",groups = Login.class)
	String usuario;
	@NotNull(message = "campo requerido contraseña",groups = Login.class)
	@NotEmpty(message = "campo contraseña no puede estar en blanco",groups = Login.class)
	String contrasena;
	String correo;
    String token;
	boolean estado;
}
