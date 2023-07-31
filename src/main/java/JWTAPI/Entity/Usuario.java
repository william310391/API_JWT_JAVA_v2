package JWTAPI.Entity;

import java.io.Serializable;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@AttributeOverrides({
	@AttributeOverride(name="id",column=@Column(name="IDUSUARIO"))
})
@Table(name = "Usuario")
public class Usuario extends BaseEntity implements Serializable {

	@Column(name = "nombres")
	String nombres;
	@Column(name = "apellidos")
	String apellidos; 
	@Column(name = "cuenta")
	String cuenta;
	@Column(name = "contrasena")
	String contrasena;
	@Column(name = "correo")
	String correo;
  
}