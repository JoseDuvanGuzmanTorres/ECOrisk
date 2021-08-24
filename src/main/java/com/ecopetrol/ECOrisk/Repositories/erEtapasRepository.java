package com.ecopetrol.ECOrisk.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ecopetrol.ECOrisk.Models.erEtapas;

@Repository
public interface erEtapasRepository extends JpaRepository<erEtapas, Integer> {

	@Query(value="select * from er_etapas e where e.ee_etapaproyecto = :etapa LIMIT 1", nativeQuery=true)
    erEtapas findByEtapa(@Param("etapa") String etapa);
	
	@Query(value="select * from er_etapas ORDER BY ee_etapaproyecto ASC", nativeQuery=true)
	List<erEtapas> findByAllAsc();
}
