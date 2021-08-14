package com.ecopetrol.ECOrisk.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ecopetrol.ECOrisk.Models.erEstados;

@Repository
public interface erEstadosRepository extends JpaRepository<erEstados, Integer> {

	@Query(value="select * from er_estados e where e.er_estado = :estado LIMIT 1", nativeQuery=true)
	erEstados findByEstado(@Param("estado") String estado);
	
	@Query(value="select * from er_estados ORDER BY er_estado ASC", nativeQuery=true)
    List<erEstados> findByAllAsc();
	
}
