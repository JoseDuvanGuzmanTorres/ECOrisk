package com.ecopetrol.ECOrisk.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecopetrol.ECOrisk.Models.erEspecialidad;

@Repository
public interface erEspecialidadRepository extends JpaRepository<erEspecialidad, Integer> {

	@Query(value="select * from er_especialidad e where e.es_especialidad = :especialidad LIMIT 1", nativeQuery=true)
	erEspecialidad findByEspecialidad(@Param("especialidad") String especialidad);
	
	@Query(value="select * from er_especialidad ORDER BY es_especialidad ASC", nativeQuery=true)
	List<erEspecialidad> findByAllAsc();
}
