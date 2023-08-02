package JWTAPI.DTO.MapperConfig;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import JWTAPI.DTO.RolDTO;
import JWTAPI.Entity.Rol;

@Mapper
public interface RolMapper {
     RolMapper INSTANCE = Mappers.getMapper(RolMapper.class);

     Rol rolDTOToRol(RolDTO rolDTO);
     RolDTO rolToRolDTO (Rol rol);

     List<RolDTO> ListaRolToRolDTO(List<Rol> ent);

}
