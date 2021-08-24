package com.ecopetrol.ECOrisk.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecopetrol.ECOrisk.Models.erRiesgos_Consecuencias;

@Repository
public interface erRiesgos_ConsecuenciasRepository extends JpaRepository<erRiesgos_Consecuencias, Integer> {

	@Query(value="select * from er_riesgos_consecuencias e where e.rc = :valoracion LIMIT 1", nativeQuery=true)
    erRiesgos_Consecuencias findByValoracion(@Param("valoracion") String valoracion);
	
	@Query(value="select * from er_riesgos_consecuencias e ORDER BY e.rc ASC", nativeQuery=true)
    List<erRiesgos_Consecuencias> findByAllAsc();
}
