package com.ecopetrol.ECOrisk.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ecopetrol.ECOrisk.Models.erGerencias;

@Repository
public interface erGerenciasRepository extends JpaRepository<erGerencias, Integer> {
	
	@Query(value="select * from er_gerencias e where e.eg_gerenciacliente = BINARY :gerencia LIMIT 1", nativeQuery=true)
    erGerencias findByGerencia(@Param("gerencia") String gerencia);
	
	@Query(value="select * from er_gerencias ORDER BY eg_gerenciacliente ASC", nativeQuery=true)
	List<erGerencias> findByAllAsc();
}
