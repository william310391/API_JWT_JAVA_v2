package JWTAPI.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;


import lombok.Data;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RolDTO {
    int id;
    String nombre;
    String descripcion;
    boolean estado;
}
