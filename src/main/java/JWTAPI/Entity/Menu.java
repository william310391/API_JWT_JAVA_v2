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
	@AttributeOverride(name="id",column=@Column(name="IDMENU"))
})
@Table(name = "Menu")
public class Menu extends BaseEntity implements Serializable {
    @Column(name = "nombre")
    String nombre;
    @Column(name = "url")
    String url;
}
