package com.ecopetrol.ECOrisk.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.ecopetrol.ECOrisk.Models.erEstadopro;

@Repository
public interface erEstadoproyectoRepository extends JpaRepository<erEstadopro, Integer> {

	@Query(value="select * from er_estado_proyecto es where es.estadopro = :estado LIMIT 1", nativeQuery=true)
	erEstadopro findByEstadopro(@Param("estado") String estado);
	
	@Query(value="select * from er_estado_proyecto ORDER BY estadopro ASC", nativeQuery=true)
    List<erEstadopro> findByAllAsc();


	
	
	
}

