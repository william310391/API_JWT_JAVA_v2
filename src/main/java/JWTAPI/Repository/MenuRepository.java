package JWTAPI.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import JWTAPI.Entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Integer>{
    @Query(value = "select * from menu where idMenu in(:cadenaidMenu) and estado=1",nativeQuery= true)
    List<Menu> findMenuByIdMenu(@Param("cadenaidMenu") String cadenaidMenu);  
}
