package com.ecopetrol.ECOrisk.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecopetrol.ECOrisk.Models.erRiesgos_Valoracion;
import com.ecopetrol.ECOrisk.Projections.erRiesgos_ValoracionProjection;

@Repository
public interface erRiesgos_ValoracionRepository extends JpaRepository<erRiesgos_Valoracion, Integer> {

	@Query(value="select * from er_riesgos_valoracion e where e.rv = :valoracion LIMIT 1", nativeQuery=true)
    erRiesgos_Valoracion findByValoracion(@Param("valoracion") String valoracion);
	
	@Query(value="select * from er_riesgos_valoracion e ORDER BY e.rv ASC", nativeQuery=true)
    List<erRiesgos_Valoracion> findByAllAsc();
    
    @Query(value="SELECT SUM(CASE WHEN e.ra_rv_id = 1 AND en.er_proceso_id = 1 AND (e.er_cierre_id = 2 OR e.er_cierre_id IS NULL) THEN 1 ELSE 0 END) as InicialCapexN"
 +" ,SUM(CASE WHEN e.ra_rv_id = 2 AND en.er_proceso_id = 1 AND (e.er_cierre_id = 2 OR e.er_cierre_id IS NULL) THEN 1 ELSE 0 END) as InicialCapexL"
 +" ,SUM(CASE WHEN e.ra_rv_id = 3 AND en.er_proceso_id = 1 AND (e.er_cierre_id = 2 OR e.er_cierre_id IS NULL) THEN 1 ELSE 0 END) as InicialCapexM"
 +" ,SUM(CASE WHEN e.ra_rv_id = 4 AND en.er_proceso_id = 1 AND (e.er_cierre_id = 2 OR e.er_cierre_id IS NULL) THEN 1 ELSE 0 END) as InicialCapexH"
 +" ,SUM(CASE WHEN e.ra_rv_id = 5 AND en.er_proceso_id = 1 THEN 1 ELSE 0 END) as InicialCapexVH"
 +" ,SUM(CASE WHEN e.rr_rv_id = 1 AND en.er_proceso_id = 1 THEN 1 ELSE 0 END) as ResidualCapexN"
 +" ,SUM(CASE WHEN e.rr_rv_id = 2 AND en.er_proceso_id = 1 THEN 1 ELSE 0 END) as ResidualCapexL"
 +" ,SUM(CASE WHEN e.rr_rv_id = 3 AND en.er_proceso_id = 1 THEN 1 ELSE 0 END) as ResidualCapexM"
 +" ,SUM(CASE WHEN e.rr_rv_id = 4 AND en.er_proceso_id = 1 THEN 1 ELSE 0 END) as ResidualCapexH"
 +" ,SUM(CASE WHEN e.rr_rv_id = 5 AND en.er_proceso_id = 1 THEN 1 ELSE 0 END) as ResidualCapexVH"
 +" ,SUM(CASE WHEN e.rr_rv_id = 1 AND en.er_proceso_id = 1 AND e.er_estado_id = 2 AND (e.er_cierre_id = 2 OR e.er_cierre_id IS NULL) THEN 1 ELSE 0 END) as CerradosCapexN"
 +" ,SUM(CASE WHEN e.rr_rv_id = 2 AND en.er_proceso_id = 1 AND e.er_estado_id = 2 AND (e.er_cierre_id = 2 OR e.er_cierre_id IS NULL) THEN 1 ELSE 0 END) as CerradosCapexL"
 +" ,SUM(CASE WHEN e.rr_rv_id = 3 AND en.er_proceso_id = 1 AND e.er_estado_id = 2 AND (e.er_cierre_id = 2 OR e.er_cierre_id IS NULL) THEN 1 ELSE 0 END) as CerradosCapexM"
 +" ,SUM(CASE WHEN e.rr_rv_id = 4 AND en.er_proceso_id = 1 AND e.er_estado_id = 2 AND (e.er_cierre_id = 2 OR e.er_cierre_id IS NULL) THEN 1 ELSE 0 END) as CerradosCapexH"
 +" ,SUM(CASE WHEN e.rr_rv_id = 5 AND en.er_proceso_id = 1 AND e.er_estado_id = 2 AND (e.er_cierre_id = 2 OR e.er_cierre_id IS NULL) THEN 1 ELSE 0 END) as CerradosCapexVH"
 +" ,SUM(CASE WHEN e.ra_rv_id = 1 AND en.er_proceso_id = 1 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) as AbiertosCapexN"
 +" ,SUM(CASE WHEN e.ra_rv_id = 2 AND en.er_proceso_id = 1 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) as AbiertosCapexL"
 +" ,SUM(CASE WHEN e.ra_rv_id = 3 AND en.er_proceso_id = 1 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) as AbiertosCapexM"
 +" ,SUM(CASE WHEN e.ra_rv_id = 4 AND en.er_proceso_id = 1 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) as AbiertosCapexH"
 +" ,SUM(CASE WHEN e.ra_rv_id = 5 AND en.er_proceso_id = 1 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) as AbiertosCapexVH"
 +" ,SUM(CASE WHEN e.ra_rv_id = 5 AND en.er_proceso_id = 1 AND e.er_estado_id = 4 THEN 1 ELSE 0 END) as CongeladosCapexVH"
 +" ,SUM(CASE WHEN e.ra_rv_id = 4 AND en.er_proceso_id = 1 AND e.er_estado_id = 4 THEN 1 ELSE 0 END) as CongeladosCapexH"
 +" ,SUM(CASE WHEN e.ra_rv_id = 3 AND en.er_proceso_id = 1 AND e.er_estado_id = 4 THEN 1 ELSE 0 END) as CongeladosCapexM"
 +" ,SUM(CASE WHEN e.ra_rv_id = 2 AND en.er_proceso_id = 1 AND e.er_estado_id = 4 THEN 1 ELSE 0 END) as CongeladosCapexL"
 +" ,SUM(CASE WHEN e.ra_rv_id = 1 AND en.er_proceso_id = 1 AND e.er_estado_id = 4 THEN 1 ELSE 0 END) as CongeladosCapexN"		
 +" ,SUM(CASE WHEN e.ra_rv_id = 1 AND en.er_proceso_id = 2 AND (e.er_cierre_id = 2 OR e.er_cierre_id IS NULL) THEN 1 ELSE 0 END) as InicialOpexN"
 +" ,SUM(CASE WHEN e.ra_rv_id = 2 AND en.er_proceso_id = 2 AND (e.er_cierre_id = 2 OR e.er_cierre_id IS NULL) THEN 1 ELSE 0 END) as InicialOpexL"
 +" ,SUM(CASE WHEN e.ra_rv_id = 3 AND en.er_proceso_id = 2 AND (e.er_cierre_id = 2 OR e.er_cierre_id IS NULL) THEN 1 ELSE 0 END) as InicialOpexM"
 +" ,SUM(CASE WHEN e.ra_rv_id = 4 AND en.er_proceso_id = 2 AND (e.er_cierre_id = 2 OR e.er_cierre_id IS NULL) THEN 1 ELSE 0 END) as InicialOpexH"
 +" ,SUM(CASE WHEN e.ra_rv_id = 5 AND en.er_proceso_id = 2 AND (e.er_cierre_id = 2 OR e.er_cierre_id IS NULL) THEN 1 ELSE 0 END) as InicialOpexVH"
 +" ,SUM(CASE WHEN e.rr_rv_id = 1 AND en.er_proceso_id = 2 THEN 1 ELSE 0 END) as ResidualOpexN"
 +" ,SUM(CASE WHEN e.rr_rv_id = 2 AND en.er_proceso_id = 2 THEN 1 ELSE 0 END) as ResidualOpexL"
 +" ,SUM(CASE WHEN e.rr_rv_id = 3 AND en.er_proceso_id = 2 THEN 1 ELSE 0 END) as ResidualOpexM"
 +" ,SUM(CASE WHEN e.rr_rv_id = 4 AND en.er_proceso_id = 2 THEN 1 ELSE 0 END) as ResidualOpexH"
 +" ,SUM(CASE WHEN e.rr_rv_id = 5 AND en.er_proceso_id = 2 THEN 1 ELSE 0 END) as ResidualOpexVH"
 +" ,SUM(CASE WHEN e.rr_rv_id = 1 AND en.er_proceso_id = 2 AND e.er_estado_id = 2 AND (e.er_cierre_id = 2 OR e.er_cierre_id IS NULL) THEN 1 ELSE 0 END) as CerradosOpexN"
 +" ,SUM(CASE WHEN e.rr_rv_id = 2 AND en.er_proceso_id = 2 AND e.er_estado_id = 2 AND (e.er_cierre_id = 2 OR e.er_cierre_id IS NULL) THEN 1 ELSE 0 END) as CerradosOpexL"
 +" ,SUM(CASE WHEN e.rr_rv_id = 3 AND en.er_proceso_id = 2 AND e.er_estado_id = 2 AND (e.er_cierre_id = 2 OR e.er_cierre_id IS NULL) THEN 1 ELSE 0 END) as CerradosOpexM"
 +" ,SUM(CASE WHEN e.rr_rv_id = 4 AND en.er_proceso_id = 2 AND e.er_estado_id = 2 AND (e.er_cierre_id = 2 OR e.er_cierre_id IS NULL) THEN 1 ELSE 0 END) as CerradosOpexH"
 +" ,SUM(CASE WHEN e.rr_rv_id = 5 AND en.er_proceso_id = 2 AND e.er_estado_id = 2 AND (e.er_cierre_id = 2 OR e.er_cierre_id IS NULL) THEN 1 ELSE 0 END) as CerradosOpexVH"
 +" ,SUM(CASE WHEN e.ra_rv_id = 1 AND en.er_proceso_id = 2 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) as AbiertosOpexN"
 +" ,SUM(CASE WHEN e.ra_rv_id = 2 AND en.er_proceso_id = 2 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) as AbiertosOpexL"
 +" ,SUM(CASE WHEN e.ra_rv_id = 3 AND en.er_proceso_id = 2 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) as AbiertosOpexM"
 +" ,SUM(CASE WHEN e.ra_rv_id = 4 AND en.er_proceso_id = 2 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) as AbiertosOpexH"
 +" ,SUM(CASE WHEN e.ra_rv_id = 5 AND en.er_proceso_id = 2 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) as AbiertosOpexVH"
 +" ,SUM(CASE WHEN e.ra_rv_id = 5 AND en.er_proceso_id = 2 AND e.er_estado_id = 4 THEN 1 ELSE 0 END) as CongeladosOpexVH"
 +" ,SUM(CASE WHEN e.ra_rv_id = 4 AND en.er_proceso_id = 2 AND e.er_estado_id = 4 THEN 1 ELSE 0 END) as CongeladosOpexH"
 +" ,SUM(CASE WHEN e.ra_rv_id = 3 AND en.er_proceso_id = 2 AND e.er_estado_id = 4 THEN 1 ELSE 0 END) as CongeladosOpexM"
 +" ,SUM(CASE WHEN e.ra_rv_id = 2 AND en.er_proceso_id = 2 AND e.er_estado_id = 4 THEN 1 ELSE 0 END) as CongeladosOpexL"
 +" ,SUM(CASE WHEN e.ra_rv_id = 1 AND en.er_proceso_id = 2 AND e.er_estado_id = 4 THEN 1 ELSE 0 END) as CongeladosOpexN"
 +"  FROM er_hoja_trabajo e, er_encabezado en WHERE en.er_encabezado_id = e.er_encabezado_id", nativeQuery=true) erRiesgos_ValoracionProjection findAllProjection();
}
