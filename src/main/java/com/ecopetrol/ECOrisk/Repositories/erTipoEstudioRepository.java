package com.ecopetrol.ECOrisk.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ecopetrol.ECOrisk.Models.erTipoEstudio;

@Repository
public interface erTipoEstudioRepository extends JpaRepository<erTipoEstudio, Integer> {

	@Query(value="select * from er_tipo_estudio e where e.er_tipoestudio = :tipoestudio LIMIT 1", nativeQuery=true)
	erTipoEstudio findByTipoEstudio(@Param("tipoestudio") String tipoestudio);
	
	@Query(value="select * from er_tipo_estudio ORDER BY er_tipoestudio ASC", nativeQuery=true)
    List<erTipoEstudio> findByAllAsc();
	
}
