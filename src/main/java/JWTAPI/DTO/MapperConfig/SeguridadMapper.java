package JWTAPI.DTO.MapperConfig;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import JWTAPI.DTO.SeguridadDTO;
import JWTAPI.Entity.Seguridad;

@Mapper
public interface SeguridadMapper {
    SeguridadMapper INSTANCE = Mappers.getMapper(SeguridadMapper.class);

    Seguridad seguridadDTOToSeguridad(SeguridadDTO seguridadDTO);
    @Mapping(target = "token", ignore = true) 
    @Mapping(target = "contrasena", ignore = true)      // afecta tambien a listas entidad a DTO
    SeguridadDTO seguridadToSeguridadDTO(Seguridad seguridad);

}
