package com.ecopetrol.ECOrisk.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ecopetrol.ECOrisk.Models.erIndicadoresFunSemanal;
import com.ecopetrol.ECOrisk.Projections.erIndicadoresFunSemanalProjection;

@Repository
public interface erIndicadoresFunSemanalRepository extends JpaRepository<erIndicadoresFunSemanal, Integer> {

	@Query(value="select u.fullname as Nomdue, ifun.* from er_indicadores_fun_semanal ifun"
			+ " INNER JOIN users u ON (u.id = ifun.iddue AND u.id = COALESCE( :dueno , ifun.iddue))", nativeQuery=true)
    List<erIndicadoresFunSemanalProjection> findAllProjectionByFuncionario(@Param("dueno") Integer dueno);
	
}
