package JWTAPI.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import JWTAPI.Entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{
    
    @Query(value = "select * from usuario where cuenta=:cuenta",nativeQuery= true)
    Usuario findUsuarioByCuenta(@Param("cuenta") String cuenta);    
}

