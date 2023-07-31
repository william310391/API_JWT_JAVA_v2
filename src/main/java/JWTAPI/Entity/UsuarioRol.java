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
	@AttributeOverride(name="id",column=@Column(name="IDUSUARIOROL"))
})
@Table(name = "Usuario_Rol")
public class UsuarioRol extends BaseEntity implements Serializable {
    @Column(name = "idUsuario")
    int idUsuario;
    @Column(name = "idRol")
    int idRol;    
}
