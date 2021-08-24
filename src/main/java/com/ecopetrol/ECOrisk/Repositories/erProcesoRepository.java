package com.ecopetrol.ECOrisk.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ecopetrol.ECOrisk.Models.erProceso;

@Repository
public interface erProcesoRepository extends JpaRepository<erProceso, Integer> {

	@Query(value="select * from er_proceso e where e.er_proceso = :proceso LIMIT 1", nativeQuery=true)
	erProceso findByProceso(@Param("proceso") String proceso);
	
	@Query(value="select * from er_proceso ORDER BY er_proceso ASC", nativeQuery=true)
    List<erProceso> findByAllAsc();
	
    
    
    
}
