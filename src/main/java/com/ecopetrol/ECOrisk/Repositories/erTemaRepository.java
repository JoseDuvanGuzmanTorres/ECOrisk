package com.ecopetrol.ECOrisk.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ecopetrol.ECOrisk.Models.erTema;

@Repository
public interface erTemaRepository extends JpaRepository<erTema, Integer> {

	@Query(value="select * from er_tema e where e.er_tema = :tema LIMIT 1", nativeQuery=true)
	erTema findByTema(@Param("tema") String tema);
	
	
}
