package com.ecopetrol.ECOrisk.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ecopetrol.ECOrisk.Models.erProyecto;
import com.ecopetrol.ECOrisk.Projections.IndicadoresProjection;
import com.ecopetrol.ECOrisk.Projections.erProyectoProjection;

@Repository
public interface erProyectoRepository extends JpaRepository<erProyecto, Integer> {

	@Query(value="select * from er_proyecto e where e.er_proyecto = :codigo_proyecto LIMIT 1", nativeQuery=true)
    erProyecto findByProyectoCod(@Param("codigo_proyecto") String codigo_proyecto);
	
	@Query(value="select e.* from er_proyecto e, er_encabezado en where e.er_proyecto_id = en.er_proyecto_id GROUP BY e.er_proyecto_id", nativeQuery=true)
	List<erProyecto> findByEncabezadoProId();
	
	@Query(value="select e.* from er_proyecto e, er_encabezado en,er_proceso proc where e.er_proyecto_id = en.er_proyecto_id AND en.e_liderproyecto = :dueno GROUP BY e.er_proyecto_id", nativeQuery=true)
	List<erProyecto> findByDueno(@Param("dueno") String dueno);

	
	//Projection
	
	@Query(value="SELECT distinct pro.er_proyecto_id as proid, pro.er_proyecto as Codpro, en.e_nombreproyecto as nompro, u.id as iddue ,u.fullname as nomdue, (x.eventos + y.eventos2) as eventos ,x.Riesgosvh as Riesgosvh, x.Riesgosh as Riesgosh ,  x.Riesgosm as Riesgosm ,x.Riesgosl as Riesgosl"
		+	" ,(Riesgosl + Riesgosm + Riesgosh + Riesgosvh) as Riesgostotal , x.Riesgosvha as Riesgosvha, x.Riesgosha as Riesgosha ,  x.Riesgosma as Riesgosma ,x.Riesgosla as Riesgosla , x.Abiertostotal as Abiertostotal, x.Abiertosvencidos as Abiertosvencidos, x.Abiertosatiempo as Abiertosatiempo , x.Cerradostotales as Cerradostotal, x.gaps as gaps"
		+	" , ROUND((gaps/Cerradostotales),0) as Promgaps ,ROUND(((Cerradostotales /(Cerradostotales+Abiertostotal) )*100),0) as Cumplimiento , x.Whatif as Whatif , x.Constructabilidad as Constructabilidad, x.Dreview as Dreview, x.Preview as Preview, prc.er_proceso as Proceso"
		+	" FROM er_proyecto pro"
		+	" INNER JOIN er_encabezado en ON en.er_proyecto_id = pro.er_proyecto_id" 
		+	" INNER JOIN er_hoja_trabajo e ON e.er_encabezado_id = en.er_encabezado_id"
		+	" INNER JOIN users u ON (u.id = en.e_liderproyecto AND u.id != 43 AND u.id = COALESCE( :dueno ,en.e_liderproyecto))"
		+	" INNER JOIN er_proceso prc ON prc.er_proceso_id = en.er_proceso_id"
		+	" INNER JOIN("
		+	" SELECT pro1.er_proyecto Codpro, COUNT(distinct e1.ht_evento) as eventos, SUM(CASE WHEN e1.ra_rv_id = 2 AND e1.er_estado_id = 1 THEN 1 ELSE 0 END) AS Riesgosla, SUM(CASE WHEN e1.ra_rv_id = 3 AND e1.er_estado_id = 1 THEN 1 ELSE 0 END) AS Riesgosma" 
		+	" , SUM(CASE WHEN e1.ra_rv_id = 4 AND e1.er_estado_id = 1 THEN 1 ELSE 0 END) AS Riesgosha, SUM(CASE WHEN e1.ra_rv_id = 5 AND e1.er_estado_id = 1 THEN 1 ELSE 0 END) AS Riesgosvha , SUM(CASE WHEN e1.er_estado_id = 1 THEN 1 ELSE 0 END) AS Abiertostotal"
		+ 	" , SUM(CASE WHEN e1.ra_rv_id = 2 THEN 1 ELSE 0 END) AS Riesgosl, SUM(CASE WHEN e1.ra_rv_id = 3 THEN 1 ELSE 0 END) AS Riesgosm, SUM(CASE WHEN e1.ra_rv_id = 4 THEN 1 ELSE 0 END) AS Riesgosh, SUM(CASE WHEN e1.ra_rv_id = 5 THEN 1 ELSE 0 END) AS Riesgosvh"
		+	" , SUM(CASE WHEN e1.er_estado_id = 1 AND e1.er_oportunidad_id = 1 THEN 1 ELSE 0 END) AS Abiertosvencidos , SUM(CASE WHEN e1.er_estado_id = 1 AND e1.er_oportunidad_id = 2 THEN 1 ELSE 0 END) AS Abiertosatiempo"
		+	" , SUM(CASE WHEN e1.er_estado_id = 2 THEN 1 ELSE 0 END) AS Cerradostotales , SUM(e1.gap) AS gaps, SUM(DISTINCT CASE WHEN en1.er_tipoestudio_id = 1 THEN 1 ELSE 0 END) AS Whatif"
		+	" , SUM(DISTINCT CASE WHEN en1.er_tipoestudio_id = 2 THEN 1 ELSE 0 END) AS Constructabilidad, SUM(DISTINCT CASE WHEN en1.er_tipoestudio_id = 3 THEN 1 ELSE 0 END) AS Dreview"
		+	" , SUM(DISTINCT CASE WHEN en1.er_tipoestudio_id = 4 THEN 1 ELSE 0 END) AS Preview"
		+	" FROM er_proyecto pro1"
		+	" INNER JOIN er_encabezado en1 ON en1.er_proyecto_id = pro1.er_proyecto_id"
		+	" INNER JOIN er_hoja_trabajo e1 ON e1.er_encabezado_id = en1.er_encabezado_id"
		+	" group by pro1.er_proyecto"
		+	" ) as x ON x.Codpro = pro.er_proyecto"
		+	" INNER JOIN("
		+	" SELECT pro.er_proyecto as proyecto, COUNT(DISTINCT e.ht_pregunta) as eventos2 FROM er_proyecto pro"
		+	" INNER JOIN er_encabezado en ON (en.er_proyecto_id = pro.er_proyecto_id  )"
		+	" INNER JOIN users u ON u.id = en.e_liderproyecto "
		+	" LEFT OUTER JOIN er_hoja_trabajo e ON (e.er_encabezado_id = en.er_encabezado_id AND (en.er_tipoestudio_id = 3 OR en.er_tipoestudio_id = 4))" 
		+	" GROUP BY pro.er_proyecto"
		+	" ) AS y ON y.proyecto = pro.er_proyecto"
			, nativeQuery=true)
	List<IndicadoresProjection> findAllIndicadoresProyecto(@Param("dueno") Integer dueno);
	
	
	//indicadores talleres Lecciones Aprendidas 
	@Query(value="SELECT distinct pro.er_proyecto_id as proid, pro.er_proyecto as Codpro, en.e_nombreproyecto as nompro, u.id as iddue ,u.fullname as nomdue, (x.eventos + y.eventos2) as eventos "
			+ "	, x.Abiertostotal as Abiertostotal, x.Abiertosvencidos as Abiertosvencidos, x.Abiertosatiempo as Abiertosatiempo , x.Cerradostotales as Cerradostotal, x.gaps as gaps"
			+ "	, ROUND((gaps/Cerradostotales),0) as Promgaps ,ROUND(((Cerradostotales /(Cerradostotales+Abiertostotal) )*100),0) as Cumplimiento , x.LeccionesAprendidas as LeccionesAprendidas, prc.er_proceso as Proceso "
			+ "	 FROM er_proyecto pro"
			+ " 	INNER JOIN er_encabezado en ON en.er_proyecto_id = pro.er_proyecto_id"
			+ "	INNER JOIN er_hoja_trabajo e ON e.er_encabezado_id = en.er_encabezado_id"
			+	" INNER JOIN users u ON (u.id = en.e_liderproyecto AND u.id = COALESCE( :dueno ,en.e_liderproyecto))"
			+ "	INNER JOIN er_proceso prc ON prc.er_proceso_id = en.er_proceso_id"
			+ "	INNER JOIN("
			+ "	SELECT pro1.er_proyecto Codpro, COUNT(distinct e1.ht_evento) as eventos"
			+ "	, SUM(CASE WHEN e1.er_estado_id = 1 THEN 1 ELSE 0 END) AS Abiertostotal"
			+ "	, SUM(CASE WHEN e1.er_estado_id = 1 AND e1.er_oportunidad_id = 1 THEN 1 ELSE 0 END) AS Abiertosvencidos , SUM(CASE WHEN e1.er_estado_id = 1 AND e1.er_oportunidad_id = 2 THEN 1 ELSE 0 END) AS Abiertosatiempo"
			+ "	, SUM(CASE WHEN e1.er_estado_id = 2 THEN 1 ELSE 0 END) AS Cerradostotales , SUM(e1.gap) AS gaps"
			+ "    , SUM(DISTINCT CASE WHEN en1.er_tipoestudio_id = 6 THEN 1 ELSE 0 END) AS LeccionesAprendidas"
			+ "	FROM er_proyecto pro1"
			+ "	INNER JOIN er_encabezado en1 ON en1.er_proyecto_id = pro1.er_proyecto_id"
			+ "	INNER JOIN er_hoja_trabajo e1 ON e1.er_encabezado_id = en1.er_encabezado_id"
			+ "	group by pro1.er_proyecto"
			+ "	) as x ON x.Codpro = pro.er_proyecto"
			+ "	INNER JOIN("
			+ "	SELECT pro.er_proyecto as proyecto, COUNT(DISTINCT e.ht_pregunta) as eventos2 FROM er_proyecto pro"
			+ "	INNER JOIN er_encabezado en ON (en.er_proyecto_id = pro.er_proyecto_id  )"
			+ "	INNER JOIN users u ON u.id = en.e_liderproyecto"
			+ "	 INNER JOIN er_hoja_trabajo e ON (e.er_encabezado_id = en.er_encabezado_id AND (en.er_tipoestudio_id = 6 )) "
			+ "	GROUP BY pro.er_proyecto"
			+ "	) AS y ON y.proyecto = pro.er_proyecto"
			, nativeQuery=true)
	List<IndicadoresProjection> findAllIndicadoresLA(@Param("dueno") Integer dueno);
	
	
	@Query(value=" SELECT '' as Proid, '' as Codpro , '' as Nompro, u.id as iddue , u.fullname as Nomdue, (COUNT(distinct e.ht_evento) + y.eventos2) as Eventos,  SUM(CASE WHEN e.ra_rv_id = 5 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) AS Riesgosvha , SUM(CASE WHEN e.ra_rv_id = 4 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) AS Riesgosha"
			+	" , SUM(CASE WHEN e.ra_rv_id = 3 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) AS Riesgosma , SUM(CASE WHEN e.ra_rv_id = 2 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) AS Riesgosla"
			+	" , SUM(CASE WHEN (e.ra_rv_id = 2 OR e.ra_rv_id = 3 OR e.ra_rv_id = 4 OR e.ra_rv_id = 5) THEN 1 ELSE 0 END) AS Riesgostotal,  SUM(CASE WHEN e.er_estado_id = 1 THEN 1 ELSE 0 END) AS Abiertostotal"
			+	" , SUM(CASE WHEN e.er_estado_id = 1 AND e.er_oportunidad_id = 1 THEN 1 ELSE 0 END) AS Abiertosvencidos , SUM(CASE WHEN e.er_estado_id = 1 AND e.er_oportunidad_id = 2 THEN 1 ELSE 0 END) AS Abiertosatiempo"
			+	" , SUM(CASE WHEN e.ra_rv_id = 5 THEN 1 ELSE 0 END) AS Riesgosvh , SUM(CASE WHEN e.ra_rv_id = 4 THEN 1 ELSE 0 END) AS Riesgosh , SUM(CASE WHEN e.ra_rv_id = 3 THEN 1 ELSE 0 END) AS Riesgosm , SUM(CASE WHEN e.ra_rv_id = 2 THEN 1 ELSE 0 END) AS Riesgosl"
			+ 	" , SUM(CASE WHEN e.er_estado_id = 2 THEN 1 ELSE 0 END) AS Cerradostotal , SUM(e.gap) AS gaps ,  ROUND((SUM(e.gap)/SUM(CASE WHEN e.er_estado_id = 2 THEN 1 ELSE 0 END)),0) as Promgaps"
			+	" , ROUND((( SUM(CASE WHEN e.er_estado_id = 2 THEN 1 ELSE 0 END) / (SUM(CASE WHEN e.er_estado_id = 1 THEN 1 ELSE 0 END) +  SUM(CASE WHEN e.er_estado_id = 2 THEN 1 ELSE 0 END)  ) )*100),0) as Cumplimiento"
			+	" , x.Whatif AS Whatif , x.Constructabilidad AS Constructabilidad , x.Dreview AS Dreview , x.Preview AS Preview, '' as Proceso"
			+	" FROM er_encabezado en"			
			+	" INNER JOIN er_hoja_trabajo e ON e.er_encabezado_id = en.er_encabezado_id"
			+	" INNER JOIN users u ON (u.id = en.e_liderproyecto AND u.id = COALESCE( :dueno ,en.e_liderproyecto))"
			+	" INNER JOIN("
			+	" SELECT en1.e_liderproyecto as dueid, SUM(CASE WHEN en1.er_tipoestudio_id = 1 THEN 1 ELSE 0 END) AS Whatif, SUM(CASE WHEN en1.er_tipoestudio_id = 2 THEN 1 ELSE 0 END) AS Constructabilidad"
			+	" , SUM(CASE WHEN en1.er_tipoestudio_id = 3 THEN 1 ELSE 0 END) AS Dreview, SUM(CASE WHEN en1.er_tipoestudio_id = 4 THEN 1 ELSE 0 END) AS Preview"
			+	" FROM er_encabezado en1" 
			+	" group by en1.e_liderproyecto"
			+	" ) as x ON x.dueid = u.id"
			+	" INNER JOIN("
			+	" SELECT en.e_liderproyecto as dueid2, COUNT(DISTINCT e.ht_pregunta) as eventos2 FROM er_encabezado en" 
			+	" LEFT OUTER JOIN er_hoja_trabajo e ON (e.er_encabezado_id = en.er_encabezado_id AND (en.er_tipoestudio_id = 3 OR en.er_tipoestudio_id = 4))"
			+	" GROUP BY en.e_liderproyecto"
			+	" )AS y ON y.dueid2 = u.id"
			+	" group by en.e_liderproyecto"
				, nativeQuery=true)
		List<IndicadoresProjection> findAllIndicadoresFuncionario(@Param("dueno") Integer dueno); //
	
										//
	@Query(value=" SELECT '' as Proid, '' as Codpro , '' as Nompro, u.id as iddue , u.fullname as Nomdue, (COUNT(distinct e.ht_evento) + y.eventos2) as Eventos,  SUM(CASE WHEN e.ra_rv_id = 5 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) AS Riesgosvha , SUM(CASE WHEN e.ra_rv_id = 4 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) AS Riesgosha"
			+	" , SUM(CASE WHEN e.ra_rv_id = 3 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) AS Riesgosma , SUM(CASE WHEN e.ra_rv_id = 2 AND e.er_estado_id = 1 THEN 1 ELSE 0 END) AS Riesgosla"
			+	" , SUM(CASE WHEN (e.ra_rv_id = 2 OR e.ra_rv_id = 3 OR e.ra_rv_id = 4 OR e.ra_rv_id = 5) THEN 1 ELSE 0 END) AS Riesgostotal,  SUM(CASE WHEN e.er_estado_id = 1 THEN 1 ELSE 0 END) AS Abiertostotal"
			+	" , SUM(CASE WHEN e.er_estado_id = 1 AND e.er_oportunidad_id = 1 THEN 1 ELSE 0 END) AS Abiertosvencidos , SUM(CASE WHEN e.er_estado_id = 1 AND e.er_oportunidad_id = 2 THEN 1 ELSE 0 END) AS Abiertosatiempo"
			+	" , SUM(CASE WHEN e.ra_rv_id = 5 THEN 1 ELSE 0 END) AS Riesgosvh , SUM(CASE WHEN e.ra_rv_id = 4 THEN 1 ELSE 0 END) AS Riesgosh , SUM(CASE WHEN e.ra_rv_id = 3 THEN 1 ELSE 0 END) AS Riesgosm , SUM(CASE WHEN e.ra_rv_id = 2 THEN 1 ELSE 0 END) AS Riesgosl"
			+ 	" , SUM(CASE WHEN e.er_estado_id = 2 THEN 1 ELSE 0 END) AS Cerradostotal , SUM(e.gap) AS gaps ,  ROUND((SUM(e.gap)/SUM(CASE WHEN e.er_estado_id = 2 THEN 1 ELSE 0 END)),0) as Promgaps"
			+	" , ROUND((( SUM(CASE WHEN e.er_estado_id = 2 THEN 1 ELSE 0 END) / (SUM(CASE WHEN e.er_estado_id = 1 THEN 1 ELSE 0 END) +  SUM(CASE WHEN e.er_estado_id = 2 THEN 1 ELSE 0 END)  ) )*100),0) as Cumplimiento"
			+	" , x.Whatif AS Whatif , x.Constructabilidad AS Constructabilidad , x.Dreview AS Dreview , x.Preview AS Preview,x.LeccionesA AS LeccionesA,  proc.er_proceso as Proceso"
			+	" FROM er_encabezado en"			
			+	" INNER JOIN er_hoja_trabajo e ON e.er_encabezado_id = en.er_encabezado_id"
			+	" INNER JOIN users u ON (u.id = en.e_liderproyecto AND u.id = COALESCE( :dueno ,en.e_liderproyecto))"
			+	" INNER JOIN("
			+	" SELECT en1.e_liderproyecto as dueid, SUM(CASE WHEN en1.er_tipoestudio_id = 1 THEN 1 ELSE 0 END) AS Whatif, SUM(CASE WHEN en1.er_tipoestudio_id = 2 THEN 1 ELSE 0 END) AS Constructabilidad"
			+	" , SUM(CASE WHEN en1.er_tipoestudio_id = 3 THEN 1 ELSE 0 END) AS Dreview, SUM(CASE WHEN en1.er_tipoestudio_id = 4 THEN 1 ELSE 0 END) AS Preview , SUM(CASE WHEN en1.er_tipoestudio_id = 6 THEN 1 ELSE 0 END) AS LeccionesA"
			+	" FROM er_encabezado en1" 
			+	" INNER JOIN er_proceso as proc1 ON en1.er_proceso_id = proc1.er_proceso_id"
			+	" WHERE proc1.er_proceso_id = :proceso"
			+	" group by en1.e_liderproyecto"
			+	" ) as x ON x.dueid = u.id"
			+	" INNER JOIN("
			+	" SELECT en.e_liderproyecto as dueid2, COUNT(DISTINCT e.ht_pregunta) as eventos2 FROM er_encabezado en" 
			+	" LEFT OUTER JOIN er_hoja_trabajo e ON (e.er_encabezado_id = en.er_encabezado_id AND (en.er_tipoestudio_id = 3 OR en.er_tipoestudio_id = 4))"
			+	" INNER JOIN er_proceso as proc1 ON en.er_proceso_id = proc1.er_proceso_id"
			+	" WHERE proc1.er_proceso_id = :proceso"
			+	" GROUP BY en.e_liderproyecto"
			+	" )AS y ON y.dueid2 = u.id"
			+	" INNER JOIN er_proceso as proc ON en.er_proceso_id = proc.er_proceso_id"
			+	" WHERE proc.er_proceso_id = :proceso"
			+	" group by en.e_liderproyecto"
				, nativeQuery=true)
		List<IndicadoresProjection> findAllIndicadoresFuncionarioProceso(@Param("dueno") Integer dueno,@Param("proceso") Integer proceso);
		
	
	
	
	@Query(value="SELECT distinct pro.er_proyecto_id as proid, pro.er_proyecto as Codpro, en.e_nombreproyecto as nompro, u.id as iddue , u.fullname as nomdue, x.eventos as eventos ,x.Riesgosvh as Riesgosvh, x.Riesgosh as Riesgosh ,  x.Riesgosm as Riesgosm ,x.Riesgosl as Riesgosl"
			+	" ,(Riesgosl + Riesgosm + Riesgosh + Riesgosvh) as Riesgostotal , x.Abiertostotal as Abiertostotal, x.Abiertosvencidos as Abiertosvencidos, x.Abiertosatiempo as Abiertosatiempo , x.Cerradostotales as Cerradostotal, x.gaps as gaps"
			+	" , ROUND((gaps/Cerradostotales),0) as Promgaps ,ROUND(((Cerradostotales /(Riesgosl + Riesgosm + Riesgosh + Riesgosvh) )*100),0) as Cumplimiento , x.Whatif as Whatif , x.Constructabilidad as Constructabilidad, x.Dreview as Dreview, x.Preview as Preview"
			+	" FROM er_proyecto pro"
			+	" INNER JOIN er_encabezado en ON (en.er_proyecto_id = pro.er_proyecto_id AND (en.e_liderproyecto = :lider OR en.e_lidertaller = :lider))" 
			+	" INNER JOIN er_hoja_trabajo e ON e.er_encabezado_id = en.er_encabezado_id"
			+	" INNER JOIN users u ON (u.id = en.e_liderproyecto)"
			+	" INNER JOIN("
			+	" SELECT pro1.er_proyecto Codpro, COUNT(distinct e1.ht_evento) as eventos, SUM(CASE WHEN e1.ra_rv_id = 2 THEN 1 ELSE 0 END) AS Riesgosl, SUM(CASE WHEN e1.ra_rv_id = 3 THEN 1 ELSE 0 END) AS Riesgosm" 
			+	" , SUM(CASE WHEN e1.ra_rv_id = 4 THEN 1 ELSE 0 END) AS Riesgosh, SUM(CASE WHEN e1.ra_rv_id = 5 THEN 1 ELSE 0 END) AS Riesgosvh , SUM(CASE WHEN e1.er_estado_id = 1 THEN 1 ELSE 0 END) AS Abiertostotal"
			+	" , SUM(CASE WHEN e1.er_estado_id = 1 AND e1.er_oportunidad_id = 1 THEN 1 ELSE 0 END) AS Abiertosvencidos , SUM(CASE WHEN e1.er_estado_id = 1 AND e1.er_oportunidad_id = 2 THEN 1 ELSE 0 END) AS Abiertosatiempo"
			+	" , SUM(CASE WHEN e1.er_estado_id = 2 THEN 1 ELSE 0 END) AS Cerradostotales , SUM(e1.gap) AS gaps, SUM(DISTINCT CASE WHEN en1.er_tipoestudio_id = 1 THEN 1 ELSE 0 END) AS Whatif"
			+	" , SUM(DISTINCT CASE WHEN en1.er_tipoestudio_id = 2 THEN 1 ELSE 0 END) AS Constructabilidad, SUM(DISTINCT CASE WHEN en1.er_tipoestudio_id = 3 THEN 1 ELSE 0 END) AS Dreview"
			+	" , SUM(DISTINCT CASE WHEN en1.er_tipoestudio_id = 4 THEN 1 ELSE 0 END) AS Preview"
			+	" FROM er_proyecto pro1"
			+	" INNER JOIN er_encabezado en1 ON (en1.er_proyecto_id = pro1.er_proyecto_id  AND (en1.e_liderproyecto = :lider OR en1.e_lidertaller = :lider))"
			+	" INNER JOIN er_hoja_trabajo e1 ON e1.er_encabezado_id = en1.er_encabezado_id"
			+	" WHERE e1.er_estado_id != 3 AND e1.er_estado_id != 4"
			+	" group by pro1.er_proyecto"
			+	" ) as x ON x.Codpro = pro.er_proyecto"
				, nativeQuery=true)
		List<IndicadoresProjection> findAllIndicadoresProyectoByLider(@Param("lider") Integer lider);
	
	@Query(value="SELECT pro.er_proyecto_id as Id , en.e_nombreproyecto as Nombre, pro.er_proyecto as Codigo, prc.er_proceso as Proceso FROM er_proyecto pro"
			+	" INNER JOIN er_encabezado en ON (en.er_proyecto_id = pro.er_proyecto_id)"
			+ 	" INNER JOIN er_proceso prc ON (en.er_proceso_id = prc.er_proceso_id)"
			+	" group by pro.er_proyecto_id"
				, nativeQuery=true)
	List<erProyectoProjection> findAllProyectosProjection();
	
    
    
}
