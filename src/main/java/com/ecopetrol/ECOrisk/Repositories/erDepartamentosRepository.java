package com.ecopetrol.ECOrisk.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ecopetrol.ECOrisk.Models.erDepartamentos;

@Repository
public interface erDepartamentosRepository extends JpaRepository<erDepartamentos, Integer> {

	@Query(value="select * from er_departamentos e where e.ed_deptocliente = BINARY :departamento COLLATE utf8_spanish_ci LIMIT 1", nativeQuery=true)
    erDepartamentos findByDepartamento(@Param("departamento") String departamento);
	
	@Query(value="select * from er_departamentos ORDER BY ed_deptocliente ASC", nativeQuery=true)
    List<erDepartamentos> findByAllAsc();
	
}
