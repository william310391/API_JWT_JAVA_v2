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
	@AttributeOverride(name="id",column=@Column(name="IDMENUPAGINA"))
})
@Table(name = "Menu_Pagina")
public class MenuPagina extends BaseEntity implements Serializable {
    @Column(name = "idMenu")
    int idMenu;
    @Column(name = "idPagina")
    int idPagina;
}
