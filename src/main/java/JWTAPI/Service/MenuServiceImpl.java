package JWTAPI.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import JWTAPI.Repository.MenuPaginaRepository;
import JWTAPI.Repository.MenuRepository;
import JWTAPI.Repository.PaginaRepository;
import JWTAPI.Repository.RolPaginaRepository;
import JWTAPI.Repository.RolRepository;
import JWTAPI.Repository.UsuarioRepository;
import JWTAPI.Repository.UsuarioRolRepository;
import JWTAPI.DTO.AccesosDTO;
import JWTAPI.DTO.ApiResponse;
import JWTAPI.DTO.MenuDTO;
import JWTAPI.DTO.PaginaDTO;
import JWTAPI.DTO.UsuarioDTO;
import JWTAPI.DTO.MapperConfig.MenuMapper;
import JWTAPI.DTO.MapperConfig.PaginaMapper;
import JWTAPI.DTO.MapperConfig.RolMapper;
import JWTAPI.Entity.Rol;
import JWTAPI.Entity.RolPagina;
import JWTAPI.Entity.Menu;
import JWTAPI.Entity.MenuPagina;
import JWTAPI.Entity.Pagina;
import JWTAPI.Entity.UsuarioRol;
import JWTAPI.Exception.BusinessException;

@Service
public class MenuServiceImpl implements MenuService{

    @Autowired
    private UsuarioRepository _usuarioReporepo;
    @Autowired
    private UsuarioRolRepository _usuarioRolReporepo;
    @Autowired
    private RolRepository _rolReporepo;
    @Autowired
    private RolPaginaRepository _rolMenuReporepo;
    @Autowired 
    private MenuRepository _menuReporepo;
    @Autowired 
    private MenuPaginaRepository _menuPaginaReporepo;
    @Autowired 
    private PaginaRepository _paginaReporepo;

    public ResponseEntity<?> GetAccesoByIdUsuario(UsuarioDTO dto) {

        try {
            if (!_usuarioReporepo.existsById(dto.getId())) {
                throw new BusinessException(HttpStatus.BAD_REQUEST, "El usuario ingresado no existe");
            }
            AccesosDTO accesosDTO = new AccesosDTO();
            //Roles
            List<UsuarioRol> listaUsuariorol = _usuarioRolReporepo.findRolByIdUsuario(dto.getId());            
            List<Rol> Roles = new ArrayList<>();  

            List<PaginaDTO> ListaPaginaDTO =new ArrayList<>();
            for (UsuarioRol usuarioRol : listaUsuariorol) {
                Rol rol = _rolReporepo.findById(usuarioRol.getId()).get();
                Roles.add(rol);
                List<RolPagina> listaRolPagina = new ArrayList<>();
                listaRolPagina = _rolMenuReporepo.findPaginaByIdRol(rol.getId());

                for (RolPagina rolPagina : listaRolPagina) {
                    Pagina pagina =_paginaReporepo.findById(rolPagina.getIdPagina()).get();
                    ListaPaginaDTO.add(PaginaMapper.INSTANCE.ToPaginaDTO(pagina));     
                }   
            }
            List<Integer> iDList = ListaPaginaDTO.stream().map((PaginaDTO) -> PaginaDTO.getId()).collect(Collectors.toList());
            String cadenaID= iDList.stream().map(String::valueOf).collect(Collectors.joining(","));
            List<MenuPagina> listaMenuPagina =  _menuPaginaReporepo.findMenuByIdPagina(cadenaID);

            List<Integer> iDMenuList = listaMenuPagina.stream().map((MenuPagina) -> MenuPagina.getIdMenu()).collect(Collectors.toList());
            String cadenaIDMenu= iDMenuList.stream().map(String::valueOf).collect(Collectors.joining(","));
        
            List<Menu> ListMenu =_menuReporepo.findMenuByIdMenu(cadenaIDMenu);

            
            accesosDTO.setMenus(MenuMapper.INSTANCE.ToListaMenuDTO(ListMenu));
           
            for (MenuDTO menu : accesosDTO.getMenus()) {
                for (MenuPagina menuPagina : listaMenuPagina) {
                    if (menu.getId() == menuPagina.getIdMenu()) {
                        menu.setPaginas(ListaPaginaDTO.stream().filter(x-> x.getId()==menuPagina.getIdPagina()).collect(Collectors.toList()));
                    }
                }
            }
            accesosDTO.setRoles(RolMapper.INSTANCE.ListaRolToRolDTO(Roles));

            ApiResponse<AccesosDTO> response = new ApiResponse<AccesosDTO>(accesosDTO);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
