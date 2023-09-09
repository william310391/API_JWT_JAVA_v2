package JWTAPI.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import JWTAPI.Entity.MenuPagina;

@Repository
public interface MenuPaginaRepository extends JpaRepository<MenuPagina,Integer>{
    @Query(value = "select * from menu_pagina where idPagina in(:cadenaidPagina) and estado=1",nativeQuery= true)
    List<MenuPagina> findMenuByIdPagina(@Param("cadenaidPagina") String cadenaidPagina);  
}
