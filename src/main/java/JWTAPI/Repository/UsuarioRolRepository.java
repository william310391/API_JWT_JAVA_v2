package JWTAPI.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import JWTAPI.Entity.UsuarioRol;

@Repository
public interface UsuarioRolRepository extends JpaRepository<UsuarioRol,Integer>{

    @Query(value = "select * from usuario_rol where idUsuario=:idUsuario and estado=1",nativeQuery= true)
    List<UsuarioRol> findRolByIdUsuario(@Param("idUsuario") int idUsuario);  
}
