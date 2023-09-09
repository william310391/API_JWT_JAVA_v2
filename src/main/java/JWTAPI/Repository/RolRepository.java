package JWTAPI.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import JWTAPI.Entity.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol,Integer>{
    
}
