package com.ecopetrol.ECOrisk.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecopetrol.ECOrisk.Models.erRiesgos_Probabilidad;

@Repository
public interface erRiesgos_ProbabilidadRepository extends JpaRepository<erRiesgos_Probabilidad, Integer> {

	@Query(value="select * from er_riesgos_probabilidad e where e.rp = :valoracion LIMIT 1", nativeQuery=true)
    erRiesgos_Probabilidad findByValoracion(@Param("valoracion") String valoracion);
	
	@Query(value="select * from er_riesgos_probabilidad e ORDER BY e.rp ASC", nativeQuery=true)
    List<erRiesgos_Probabilidad> findByAllAsc();
}
