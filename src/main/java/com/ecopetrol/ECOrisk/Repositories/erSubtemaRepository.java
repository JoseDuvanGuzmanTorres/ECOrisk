package com.ecopetrol.ECOrisk.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecopetrol.ECOrisk.Models.erSubtema;

@Repository
public interface erSubtemaRepository extends JpaRepository<erSubtema, Integer> {

	@Query(value="select * from er_subtema e where e.er_subtema = :subtema LIMIT 1", nativeQuery=true)
	erSubtema findBySubema(@Param("subtema") String subtema);
	
	
}
