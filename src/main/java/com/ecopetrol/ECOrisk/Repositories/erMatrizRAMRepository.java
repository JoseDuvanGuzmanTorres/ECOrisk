package com.ecopetrol.ECOrisk.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ecopetrol.ECOrisk.Models.erMatrizRAM;

@Repository
public interface erMatrizRAMRepository extends JpaRepository<erMatrizRAM, Integer> {

	@Query(value="select * from er_matrizram e where e.er_matriz = :matriz LIMIT 1", nativeQuery=true)
    erMatrizRAM findByMatriz(@Param("matriz") String matriz);
	
	@Query(value="select * from er_matrizram ORDER BY er_matriz ASC", nativeQuery=true)
    List<erMatrizRAM> findByAllAsc();
	
}
