package JWTAPI.Repository;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import JWTAPI.Entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{
    
    @Query(value = "select * from usuario where cuenta=:cuenta",nativeQuery= true)
    Usuario findUsuarioByCuenta(@Param("cuenta") String cuenta);    

    @Query(value = "select * from usuario where (nombres like '%'+:buscar+'%' or cuenta like '%'+:buscar+'%')", nativeQuery = true)
    List<Usuario> findUsuarioByNombreCuenta(@Param("buscar") String buscar);
}

