package JWTAPI.DTO.MapperConfig;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import JWTAPI.DTO.MenuDTO;
import JWTAPI.Entity.Menu;

@Mapper
public interface MenuMapper {

    MenuMapper INSTANCE = Mappers.getMapper(MenuMapper.class);
  
    Menu ToMenu(MenuDTO menuDTO);
  
    @Mapping(target = "paginas", ignore = true) 
    MenuDTO ToMenuDTO(Menu menu);

    List<MenuDTO> ToListaMenuDTO(List<Menu> Menu);
    
}
