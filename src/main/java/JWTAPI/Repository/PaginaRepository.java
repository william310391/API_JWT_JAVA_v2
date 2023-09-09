package JWTAPI.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import JWTAPI.Entity.Pagina;

@Repository
public interface PaginaRepository extends JpaRepository<Pagina,Integer>{
    
}
