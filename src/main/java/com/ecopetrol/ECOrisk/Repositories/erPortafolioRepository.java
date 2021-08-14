package com.ecopetrol.ECOrisk.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecopetrol.ECOrisk.Models.erPortafolio;

@Repository
public interface erPortafolioRepository extends JpaRepository<erPortafolio, Integer> {


	@Query(value="select * from er_portafolio e where e.er_portafolio_id = :id", nativeQuery=true)
    erPortafolio findByPortafolioId(@Param("id") Integer id);
	
	@Query(value="select * from er_portafolio e where e.er_proceso_id = :proceso", nativeQuery=true)
    List<erPortafolio> findByPortafolioProcesoId(@Param("proceso") Integer proceso);
	
	
}
