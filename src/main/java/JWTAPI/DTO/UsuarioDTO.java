package JWTAPI.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import JWTAPI.Exception.ValidationGroup.Login;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UsuarioDTO {
	
	int id;
	String nombres;	
	String apellidos;
	@NotBlank(message = "campo requerido cuenta NotBlank",groups = Login.class)
	@NotNull(message = "campo requerido cuenta NotNull",groups = Login.class)
	String cuenta;
	String correo;
	@NotNull(message = "campo requerido passssss",groups = Login.class)
	@NotEmpty(message = "campo requerido passssss",groups = Login.class)
	String contrasena;

}

