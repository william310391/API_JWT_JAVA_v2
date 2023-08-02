package JWTAPI.DTO.MapperConfig;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import JWTAPI.DTO.PaginaDTO;
import JWTAPI.Entity.Pagina;

@Mapper
public interface PaginaMapper {
    
    PaginaMapper INSTANCE = Mappers.getMapper(PaginaMapper.class);

    Pagina ToPagina(PaginaDTO paginaDTO);
    PaginaDTO ToPaginaDTO(Pagina pagina);
    List<PaginaDTO> ToListPaginaDTO(List<Pagina> ListPagina);
}
