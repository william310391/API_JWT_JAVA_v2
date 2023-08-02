package JWTAPI.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import JWTAPI.Entity.RolPagina;


@Repository
public interface RolPaginaRepository extends JpaRepository<RolPagina,Integer>{
    
    @Query(value = "select * from rol_pagina where idRol=:idRol and estado=1",nativeQuery= true)
    List<RolPagina> findPaginaByIdRol(@Param("idRol") int idRol);  
}
