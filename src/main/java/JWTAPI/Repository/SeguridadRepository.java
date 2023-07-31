package JWTAPI.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import JWTAPI.Entity.Seguridad;

@Repository
public interface SeguridadRepository extends JpaRepository<Seguridad, Integer> {
    @Query(value = "select * from seguridad where usuario=:usuario", nativeQuery = true)
    Seguridad findSeguridadByUsuario(@Param("usuario") String usuario);
}
