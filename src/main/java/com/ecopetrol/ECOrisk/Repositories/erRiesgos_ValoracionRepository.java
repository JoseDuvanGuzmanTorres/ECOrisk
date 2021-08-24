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
    
    @Query(value="SELECT  SUM(CASE WHEN e.ra_rv_id = 1 AND en.er_proceso_id = 1 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) as CRaN,  SUM(CASE WHEN e.ra_rv_id = 2 AND en.er_proceso_id = 1 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) as CRaL,  SUM(CASE WHEN e.ra_rv_id = 3 AND en.er_proceso_id = 1 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) as CRaM,  SUM(CASE WHEN e.ra_rv_id = 4 AND en.er_proceso_id = 1 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) as CRaH,  SUM(CASE WHEN e.ra_rv_id = 5 AND en.er_proceso_id = 1 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) as CRaVH"    
		    	+	  " , SUM(CASE WHEN e.rr_rv_id = 1 AND en.er_proceso_id = 1 THEN 1 ELSE 0 END) as CRrN,  SUM(CASE WHEN e.rr_rv_id = 2 AND en.er_proceso_id = 1 THEN 1 ELSE 0 END) as CRrL,  SUM(CASE WHEN e.rr_rv_id = 3 AND en.er_proceso_id = 1 THEN 1 ELSE 0 END) as CRrM,  SUM(CASE WHEN e.rr_rv_id = 4 AND en.er_proceso_id = 1 THEN 1 ELSE 0 END) as CRrH,  SUM(CASE WHEN e.rr_rv_id = 5 AND en.er_proceso_id = 1 THEN 1 ELSE 0 END) as CRrVH"
		    	+	  " , SUM(CASE WHEN e.rr_rv_id = 1 AND en.er_proceso_id = 1 AND e.er_estado_id = 2 THEN 1 ELSE 0 END) as CRraN, SUM(CASE WHEN e.rr_rv_id = 2 AND en.er_proceso_id = 1 AND e.er_estado_id = 2 THEN 1 ELSE 0 END) as CRraL, SUM(CASE WHEN e.rr_rv_id = 3 AND en.er_proceso_id = 1 AND e.er_estado_id = 2 THEN 1 ELSE 0 END) as CRraM, SUM(CASE WHEN e.rr_rv_id = 4 AND en.er_proceso_id = 1 AND e.er_estado_id = 2 THEN 1 ELSE 0 END) as CRraH, SUM(CASE WHEN e.rr_rv_id = 5 AND en.er_proceso_id = 1 AND e.er_estado_id = 2 THEN 1 ELSE 0 END) as CRraVH, SUM(CASE WHEN e.rr_rv_id = 5 AND en.er_proceso_id = 1 AND e.er_estado_id = 21 THEN 1 ELSE 0 END) as CRrabVH"
		    	+	  " , SUM(CASE WHEN e.ra_rv_id = 1 AND en.er_proceso_id = 2 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) as ORaN,  SUM(CASE WHEN e.ra_rv_id = 2 AND en.er_proceso_id = 2 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) as ORaL,  SUM(CASE WHEN e.ra_rv_id = 3 AND en.er_proceso_id = 2 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) as ORaM,  SUM(CASE WHEN e.ra_rv_id = 4 AND en.er_proceso_id = 2 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) as ORaH,  SUM(CASE WHEN e.ra_rv_id = 5 AND en.er_proceso_id = 2 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) as ORaVH"    
		    	+	  " , SUM(CASE WHEN e.rr_rv_id = 1 AND en.er_proceso_id = 2 THEN 1 ELSE 0 END) as ORrN,  SUM(CASE WHEN e.rr_rv_id = 2 AND en.er_proceso_id = 2 THEN 1 ELSE 0 END) as ORrL,  SUM(CASE WHEN e.rr_rv_id = 3 AND en.er_proceso_id = 2 THEN 1 ELSE 0 END) as ORrM,  SUM(CASE WHEN e.rr_rv_id = 4 AND en.er_proceso_id = 2 THEN 1 ELSE 0 END) as ORrH,  SUM(CASE WHEN e.rr_rv_id = 5 AND en.er_proceso_id = 2 THEN 1 ELSE 0 END) as ORrVH"
		    	+	  " , SUM(CASE WHEN e.rr_rv_id = 1 AND en.er_proceso_id = 2 AND e.er_estado_id = 2 THEN 1 ELSE 0 END) as ORraN, SUM(CASE WHEN e.rr_rv_id = 2 AND en.er_proceso_id = 2 AND e.er_estado_id = 2 THEN 1 ELSE 0 END) as ORraL, SUM(CASE WHEN e.rr_rv_id = 3 AND en.er_proceso_id = 2 AND e.er_estado_id = 2 THEN 1 ELSE 0 END) as ORraM, SUM(CASE WHEN e.rr_rv_id = 4 AND en.er_proceso_id = 2 AND e.er_estado_id = 2 THEN 1 ELSE 0 END) as ORraH, SUM(CASE WHEN e.rr_rv_id = 5 AND en.er_proceso_id = 2 AND e.er_estado_id = 2 THEN 1 ELSE 0 END) as ORraVH, SUM(CASE WHEN e.ra_rv_id = 5 AND en.er_proceso_id = 1 AND e.er_estado_id = 4 THEN 1 ELSE 0 END) as CRrcVH, SUM(CASE WHEN e.ra_rv_id = 4 AND en.er_proceso_id = 1 AND e.er_estado_id = 4 THEN 1 ELSE 0 END) as CRrcH,SUM(CASE WHEN e.ra_rv_id = 3 AND en.er_proceso_id = 1 AND e.er_estado_id = 4 THEN 1 ELSE 0 END) as CRrcM,SUM(CASE WHEN e.ra_rv_id = 2 AND en.er_proceso_id = 1 AND e.er_estado_id = 4 THEN 1 ELSE 0 END) as CRrcL, SUM(CASE WHEN e.ra_rv_id = 1 AND en.er_proceso_id = 1 AND e.er_estado_id = 4 THEN 1 ELSE 0 END) as CRrcN"
		    	+	  " FROM er_hoja_trabajo e, er_encabezado en WHERE en.er_encabezado_id = e.er_encabezado_id AND e.er_estado_id != 3", nativeQuery=true)
    erRiesgos_ValoracionProjection findAllProjection();
}
