package JWTAPI.DTO;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class AccesosDTO {
    List<RolDTO> Roles;
    List<MenuDTO> Menus;
}
