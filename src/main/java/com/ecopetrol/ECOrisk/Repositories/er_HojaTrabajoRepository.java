package com.ecopetrol.ECOrisk.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ecopetrol.ECOrisk.Models.er_HojaTrabajo;
import com.ecopetrol.ECOrisk.Projections.er_HojaTrabajoLeccionesAProjection;
import com.ecopetrol.ECOrisk.Projections.er_HojaTrabajoProjection;
import com.ecopetrol.ECOrisk.Projections.er_HojaTrabajoREmergenteProjection;
import com.ecopetrol.ECOrisk.Projections.er_HojaTrabajoRMaterializadoProjection;

@Repository
public interface er_HojaTrabajoRepository extends JpaRepository<er_HojaTrabajo, Integer> {

	
	@Query(value="SELECT e.* from er_hoja_trabajo e WHERE e.ht_fechacierre IS NULL" , nativeQuery=true)
	List<er_HojaTrabajo> findByNoFechaCierre();
	
	@Query(value="select e.* from er_hoja_trabajo e, er_encabezado en , er_especialidad es ,er_matrizram ma , er_riesgos_consecuencias rac ,er_riesgos_probabilidad rap , er_riesgos_valoracion rav  where ((e.er_encabezado_id = :encabezado_id AND e.er_encabezado_id = en.er_encabezado_id) OR (en.er_encabezado_id = e.er_encabezado_id AND en.er_encabezado_id = :encabezado_id)) AND"
			+ " e.ht_pregunta LIKE %:pregunta% AND e.ht_evento LIKE %:evento% AND e.ht_salvaguardas LIKE %:salvaguardas% AND e.ht_recomendacion LIKE %:recomendacion% AND "
			+ "((e.es_especialidad_id LIKE %:especialidad% AND es.es_especialidad_id = e.es_especialidad_id) OR (es.es_especialidad_id = e.es_especialidad_id AND es.es_especialidad LIKE %:especialidad%)) AND "
			+ "((e.er_matrizram_id LIKE %:matriz% AND ma.er_matriz_id = e.er_matrizram_id) OR (ma.er_matriz_id = e.er_matrizram_id AND ma.er_matriz LIKE %:matriz%)) AND "
			+ "((e.ra_rc_id LIKE %:raconsecuencias% AND rac.rc_id = e.ra_rc_id) OR (rac.rc_id = e.ra_rc_id AND rac.rc LIKE %:raconsecuencias%)) AND"
			+ "((e.ra_rp_id LIKE %:raprobabilidad% AND rap.rp_id = e.ra_rp_id) OR (rap.rp_id = e.ra_rp_id AND rap.rp LIKE %:raprobabilidad%)) AND"
			+ "((e.ra_rv_id LIKE %:ravaloracion% AND rav.rv_id = e.ra_rv_id) OR (rav.rv_id = e.ra_rv_id AND rav.rv LIKE %:ravaloracion%))", nativeQuery=true)
	List<er_HojaTrabajo> findByEncabezadoMultiple(@Param("encabezado_id") Integer encabezado_id, @Param("pregunta") String pregunta, @Param("evento") String evento,  @Param("especialidad") String especialidad, @Param("matriz") String matriz ,
			@Param("salvaguardas") String salvaguardas ,  @Param("raconsecuencias") String raconsecuencias, @Param("raprobabilidad") String raprobabilidad, @Param("ravaloracion") String ravaloracion, @Param("recomendacion") String recomendacion);
	
	
	@Query(value="select e.* from er_hoja_trabajo e, users u where e.ht_responsableimplementacion = u.id AND e.ht_responsableimplementacion = COALESCE( :funcionario ,u.id)" , nativeQuery=true)
	List<er_HojaTrabajo> findByFuncionario(@Param("funcionario") Integer funcionario);
	
	@Query(value="select e.* from er_hoja_trabajo e"
			+ " INNER JOIN er_encabezado en ON (en.er_encabezado_id = e.er_encabezado_id)"
			+ " INNER JOIN er_proyecto pro ON (pro.er_proyecto_id = en.er_proyecto_id AND pro.er_proyecto = :codproyecto)"
			+ " where e.er_estado_id = :estado" , nativeQuery=true)
	List<er_HojaTrabajo> findHojasByProyectoYEstado(@Param("codproyecto") String codproyecto, @Param("estado") Integer estado);
	
	@Query(value="select e.* from er_hoja_trabajo e, er_encabezado en, users u where en.e_liderproyecto = u.id AND e.er_encabezado_id = en.er_encabezado_id AND u.id = :funcionario group by e.ht_id" , nativeQuery=true)
	List<er_HojaTrabajo> findByDuenoProyecto(@Param("funcionario") Integer funcionario);
	
	@Query(value="select e.* from er_hoja_trabajo e, er_encabezado en, users u where en.e_liderproyecto = u.id AND e.er_encabezado_id = en.er_encabezado_id AND en.er_encabezado_id IN :id_encabeList AND u.id = :funcionario group by e.ht_id" , nativeQuery=true)
	List<er_HojaTrabajo> findByEncabeIdListAndDuenoProyecto(@Param("id_encabeList") List<Integer> id_encabeList,@Param("funcionario") Integer funcionario);
	
	@Query(value="select e.* from er_hoja_trabajo e, er_encabezado en where en.er_encabezado_id = e.er_encabezado_id AND en.er_encabezado_id = :encabezado_id" , nativeQuery=true)
	List<er_HojaTrabajo> findByEncabezado(@Param("encabezado_id") Integer encabezado_id);
	
	@Query(value="select e.* from er_hoja_trabajo e WHERE e.ht_id = :ht_id LIMIT 1" , nativeQuery=true)
	er_HojaTrabajo findByHt_Id(@Param("ht_id") Integer ht_id);
	
	@Query(value="select e.* from er_hoja_trabajo e WHERE e.ra_rv_id = :risk" , nativeQuery=true)
	List<er_HojaTrabajo> findByRiskActual(@Param("risk") Integer risk);
	
	@Query(value="select e.* from er_hoja_trabajo e, er_encabezado en, er_proyecto pr where e.er_encabezado_id = en.er_encabezado_id  AND en.er_proyecto_id = pr.er_proyecto_id AND pr.er_proyecto_id = :ProId" , nativeQuery=true)
	List<er_HojaTrabajo> findByProId(@Param("ProId") Integer ProId);
	
	@Query(value="select e.* from er_hoja_trabajo e, er_encabezado en, er_proyecto pro WHERE e.er_encabezado_id = en.er_encabezado_id AND en.er_proyecto_id = pro.er_proyecto_id AND pro.er_proyecto_id = :ProId AND e.ra_rv_id = :risk" , nativeQuery=true)
	List<er_HojaTrabajo> findByRiskAndProject(@Param("ProId") Integer ProId, @Param("risk") Integer risk);
	
	@Query(value="select e.* from er_hoja_trabajo e, er_encabezado en WHERE e.er_encabezado_id = en.er_encabezado_id AND e.ra_rv_id = :risk AND en.e_liderproyecto = :funcionario" , nativeQuery=true)
	List<er_HojaTrabajo> findByRiskAndFuncionario(@Param("funcionario") String funcionario, @Param("risk") Integer risk);

	@Query(value="select COUNT(distinct e.ht_evento) from er_hoja_trabajo e, er_encabezado en, er_proyecto pro where e.er_encabezado_id = en.er_encabezado_id  AND en.er_proyecto_id = pro.er_proyecto_id AND pro.er_proyecto_id = :ProId AND (e.ht_fechacierre IS NULL OR e.ht_fechacierre != en.e_fechataller)" , nativeQuery=true)
	Integer CountUniqueEventByProId(@Param("ProId") Integer ProId);
	
	
	@Query(value="select e.* from er_hoja_trabajo e, er_hoja_cambios hc where hc.ht_id = e.ht_id" , nativeQuery=true)
	List<er_HojaTrabajo> findByHojaTrabajoCambios();
	
	@Query(value="select e.* from er_hoja_trabajo e where e.er_encabezado_id IN :id_encabeList" , nativeQuery=true)
	List<er_HojaTrabajo> findHojaByEncabeIdList(@Param("id_encabeList") List<Integer> id_encabeList);
	
	@Query(value="select e.* from er_hoja_trabajo e where e.er_encabezado_id IN :id_encabeList AND e.er_estado_id = :estado" , nativeQuery=true)
	List<er_HojaTrabajo> findHojaByEncabeIdListYEstado(@Param("id_encabeList") List<Integer> id_encabeList, @Param("estado") Integer estado);
	
	@Query(value="select e.* from er_hoja_trabajo e where e.ht_responsableimplementacion = :userId AND e.er_encabezado_id IN :id_encabeList" , nativeQuery=true)
	List<er_HojaTrabajo> findHojaByEncabeIdListAndUserId(@Param("id_encabeList") List<Integer> id_encabeList, @Param("userId") Integer userId);
	
	
	
	//Projections
	@Query(value="SELECT e.ht_id as id , e.ht_fase as fase, te.er_tipoestudio as tipo, en.e_nombreproyecto as nompro ,en.e_fechataller as fechatal , en.e_codigodocumento as coddoc, es.er_estado as estado, op.er_oportunidad as oportunidad, ci.er_cierre as cierre, e.ht_pregunta as pregunta, e.ht_evento as evento, e.ht_respuestaresp as respuesta, ep.es_especialidad as especialidad, ma.er_matriz as ram, e.ht_salvaguardas as salvaguardas,"
			+	" rac.rc as raconsecuencia, rap.rp as raprobabilidad, rav.rv as ravaloracion, e.ht_recomendacion as control, rec.rc as reconsecuencia,  rep.rp as reprobabilidad, rev.rv as revaloracion, u.fullname as responsable, e.gap as gap, e.ht_fechaplaneadacierre as fechaplaneada, e.ht_fechacierre as fechacierre, e.ht_observacionesacta as observacion"
			+	" FROM er_hoja_trabajo e"
			+	" INNER JOIN er_encabezado en ON e.er_encabezado_id = en.er_encabezado_id"
			+	" INNER JOIN er_tipo_estudio te ON en.er_tipoestudio_id = te.er_tipoestudio_id"
			+	" LEFT OUTER JOIN er_estados es ON e.er_estado_id = es.er_estado_id"
			+	" LEFT OUTER JOIN er_oportunidad op ON e.er_oportunidad_id = op.er_oportunidad_id"
			+ 	" LEFT OUTER JOIN er_cierre ci ON (e.er_cierre_id = ci.er_cierre_id)"
			+	" LEFT OUTER JOIN er_especialidad ep ON e.es_especialidad_id = ep.es_especialidad_id"
			+	" LEFT OUTER JOIN er_matrizram ma ON e.er_matrizram_id = ma.er_matriz_id"
			+	" LEFT OUTER JOIN er_riesgos_consecuencias rac ON e.ra_rc_id = rac.rc_id"
			+	" LEFT OUTER JOIN er_riesgos_probabilidad rap ON e.ra_rp_id = rap.rp_id"
			+	" LEFT OUTER JOIN er_riesgos_valoracion rav ON e.ra_rv_id = rav.rv_id"
			+	" LEFT OUTER JOIN er_riesgos_consecuencias rec ON e.rr_rc_id = rec.rc_id"
			+	" LEFT OUTER JOIN er_riesgos_probabilidad rep ON e.rr_rp_id = rep.rp_id"
			+	" LEFT OUTER JOIN er_riesgos_valoracion rev ON e.rr_rv_id = rev.rv_id"
			+	" LEFT OUTER JOIN users u ON e.ht_responsableimplementacion = u.id" , nativeQuery=true)
		List<er_HojaTrabajoProjection> findAllProjection();
	

	//Projections
	@Query(value="SELECT e.ht_id as id , e.ht_fase as fase, te.er_tipoestudio as tipo, en.e_nombreproyecto as nompro ,en.e_fechataller as fechatal , en.e_codigodocumento as coddoc, es.er_estado as estado, op.er_oportunidad as oportunidad, ci.er_cierre as cierre, e.ht_pregunta as pregunta, e.ht_evento as evento, e.ht_respuestaresp as respuesta, ep.es_especialidad as especialidad, ma.er_matriz as ram, e.ht_salvaguardas as salvaguardas,"
		+	" rac.rc as raconsecuencia, rap.rp as raprobabilidad, rav.rv as ravaloracion, e.ht_recomendacion as control, rec.rc as reconsecuencia,  rep.rp as reprobabilidad, rev.rv as revaloracion, u.fullname as responsable, e.gap as gap, e.ht_fechaplaneadacierre as fechaplaneada, e.ht_fechacierre as fechacierre, e.ht_observacionesacta as observacion"
		+	" ,IF(x.hojas_ht_id = e.ht_id, 'Si','No') as Seguimiento, IF(seg.htseg_evidencia1 IS NOT NULL, 'Si', 'No') as Evidencia "
		+ 	" FROM er_hoja_trabajo e"
		+	" INNER JOIN er_encabezado en ON (e.er_encabezado_id = en.er_encabezado_id AND e.er_encabezado_id IN :id_encabeList)"
		+	" INNER JOIN er_tipo_estudio te ON (en.er_tipoestudio_id = te.er_tipoestudio_id AND en.er_tipoestudio_id < 5 AND en.er_tipoestudio_id = COALESCE( :tipo ,te.er_tipoestudio_id))"
		+	" LEFT OUTER JOIN er_estados es ON e.er_estado_id = es.er_estado_id"
		+	" LEFT OUTER JOIN er_oportunidad op ON e.er_oportunidad_id = op.er_oportunidad_id"
		+ 	" LEFT OUTER JOIN er_cierre ci ON (e.er_cierre_id = ci.er_cierre_id)"
		+	" LEFT OUTER JOIN er_especialidad ep ON e.es_especialidad_id = ep.es_especialidad_id"
		+	" LEFT OUTER JOIN er_matrizram ma ON e.er_matrizram_id = ma.er_matriz_id"
		+	" LEFT OUTER JOIN er_riesgos_consecuencias rac ON e.ra_rc_id = rac.rc_id"
		+	" LEFT OUTER JOIN er_riesgos_probabilidad rap ON e.ra_rp_id = rap.rp_id"
		+	" LEFT OUTER JOIN er_riesgos_valoracion rav ON (e.ra_rv_id = rav.rv_id)"
		+	" LEFT OUTER JOIN er_riesgos_consecuencias rec ON e.rr_rc_id = rec.rc_id"
		+	" LEFT OUTER JOIN er_riesgos_probabilidad rep ON e.rr_rp_id = rep.rp_id"
		+	" LEFT OUTER JOIN er_riesgos_valoracion rev ON e.rr_rv_id = rev.rv_id"
		+	" INNER JOIN users u ON (e.ht_responsableimplementacion = u.id AND e.ht_responsableimplementacion = COALESCE( :userId ,u.id))"
		+ 	" LEFT OUTER JOIN er_hojas_x_er_seguimiento x ON (e.ht_id = x.hojas_ht_id)"
		+ 	" LEFT OUTER JOIN er_hoja_seguimiento seg ON (x.seguimientos_htseg_id = seg.htseg_id)"
		+	 " group by e.ht_id" , nativeQuery=true)
	List<er_HojaTrabajoProjection> findAllProjectionByIdEncabeAndUserId(@Param("id_encabeList") List<Integer> id_encabeList,@Param("tipo") String tipo ,@Param("userId") Integer userId);
	
	@Query(value="SELECT e.ht_id as id , e.ht_fase as fase, te.er_tipoestudio as tipo, en.e_nombreproyecto as nompro ,en.e_fechataller as fechatal , en.e_codigodocumento as coddoc, es.er_estado as estado, op.er_oportunidad as oportunidad, ci.er_cierre as cierre, e.ht_pregunta as pregunta, e.ht_evento as evento, e.ht_respuestaresp as respuesta, ep.es_especialidad as especialidad, ma.er_matriz as ram, e.ht_salvaguardas as salvaguardas,"
		+	" rac.rc as raconsecuencia, rap.rp as raprobabilidad, rav.rv as ravaloracion, e.ht_recomendacion as control, rec.rc as reconsecuencia,  rep.rp as reprobabilidad, rev.rv as revaloracion, u.fullname as responsable, e.gap as gap, e.ht_fechaplaneadacierre as fechaplaneada, e.ht_fechacierre as fechacierre, e.ht_observacionesacta as observacion"
		+	" FROM er_hoja_trabajo e"
		+	" INNER JOIN er_encabezado en ON e.er_encabezado_id = en.er_encabezado_id"
		+	" INNER JOIN er_tipo_estudio te ON (en.er_tipoestudio_id = te.er_tipoestudio_id AND en.er_tipoestudio_id <> 6) "
		+	" INNER JOIN er_estados es ON (e.er_estado_id = es.er_estado_id AND es.er_estado_id = 1)"
		+	" LEFT OUTER JOIN er_oportunidad op ON e.er_oportunidad_id = op.er_oportunidad_id"
		+ 	" LEFT OUTER JOIN er_cierre ci ON (e.er_cierre_id = ci.er_cierre_id)"
		+	" LEFT OUTER JOIN er_especialidad ep ON e.es_especialidad_id = ep.es_especialidad_id"
		+	" LEFT OUTER JOIN er_matrizram ma ON e.er_matrizram_id = ma.er_matriz_id"
		+	" LEFT OUTER JOIN er_riesgos_consecuencias rac ON e.ra_rc_id = rac.rc_id"
		+	" LEFT OUTER JOIN er_riesgos_probabilidad rap ON e.ra_rp_id = rap.rp_id"
		+	" LEFT OUTER JOIN er_riesgos_valoracion rav ON (e.ra_rv_id = rav.rv_id AND rav.rv_id != 1)"
		+	" LEFT OUTER JOIN er_riesgos_consecuencias rec ON e.rr_rc_id = rec.rc_id"
		+	" LEFT OUTER JOIN er_riesgos_probabilidad rep ON e.rr_rp_id = rep.rp_id"
		+	" LEFT OUTER JOIN er_riesgos_valoracion rev ON e.rr_rv_id = rev.rv_id"
		+	" INNER JOIN users u ON (e.ht_responsableimplementacion = u.id AND e.ht_responsableimplementacion = :userId)" , nativeQuery=true)
	List<er_HojaTrabajoProjection> findAllProjectionAbiertasByUserId(@Param("userId") Integer userId);
		
	@Query(value="SELECT e.ht_id as id , e.ht_fase as fase, te.er_tipoestudio as tipo, en.e_nombreproyecto as nompro ,en.e_fechataller as fechatal , en.e_codigodocumento as coddoc, es.er_estado as estado, op.er_oportunidad as oportunidad, ci.er_cierre as cierre, e.ht_pregunta as pregunta, e.ht_evento as evento, e.ht_respuestaresp as respuesta, ep.es_especialidad as especialidad, ma.er_matriz as ram, e.ht_salvaguardas as salvaguardas,"
		+	" rac.rc as raconsecuencia, rap.rp as raprobabilidad, rav.rv as ravaloracion, e.ht_recomendacion as control, rec.rc as reconsecuencia,  rep.rp as reprobabilidad, rev.rv as revaloracion, u.fullname as responsable, e.gap as gap, e.ht_fechaplaneadacierre as fechaplaneada, e.ht_fechacierre as fechacierre, e.ht_observacionesacta as observacion"
		+	" FROM er_hoja_trabajo e"
		+	" INNER JOIN er_encabezado en ON (e.er_encabezado_id = en.er_encabezado_id AND en.e_liderproyecto = :userId)"
		+	" INNER JOIN er_tipo_estudio te ON en.er_tipoestudio_id = te.er_tipoestudio_id AND te.er_tipoestudio_id != 6 "
		+	" LEFT OUTER JOIN er_estados es ON (e.er_estado_id = es.er_estado_id AND es.er_estado_id = 1)"
		+	" LEFT OUTER JOIN er_oportunidad op ON e.er_oportunidad_id = op.er_oportunidad_id"
		+ 	" LEFT OUTER JOIN er_cierre ci ON (e.er_cierre_id = ci.er_cierre_id)"
		+	" LEFT OUTER JOIN er_especialidad ep ON e.es_especialidad_id = ep.es_especialidad_id"
		+	" LEFT OUTER JOIN er_matrizram ma ON e.er_matrizram_id = ma.er_matriz_id"
		+	" LEFT OUTER JOIN er_riesgos_consecuencias rac ON e.ra_rc_id = rac.rc_id"
		+	" LEFT OUTER JOIN er_riesgos_probabilidad rap ON e.ra_rp_id = rap.rp_id"
		+	" LEFT OUTER JOIN er_riesgos_valoracion rav ON e.ra_rv_id = rav.rv_id"
		+	" LEFT OUTER JOIN er_riesgos_consecuencias rec ON e.rr_rc_id = rec.rc_id"
		+	" LEFT OUTER JOIN er_riesgos_probabilidad rep ON e.rr_rp_id = rep.rp_id"
		+	" LEFT OUTER JOIN er_riesgos_valoracion rev ON e.rr_rv_id = rev.rv_id"
		+	" INNER JOIN users u ON e.ht_responsableimplementacion = u.id" , nativeQuery=true)
	List<er_HojaTrabajoProjection> findAllProjectionAbiertasByDueno(@Param("userId") Integer userId);
	
	//Lecciones Aprendidas
	@Query(value="SELECT la.ht_id as Id, ti.er_tipoestudio as Tipo, en.e_nombreproyecto as Nompro, en.e_codigodocumento as Coddoc, est.er_estado as Estado, op.er_oportunidad as Oportunidad, ci.er_cierre as Cierre, la.nombre, la.resumen, te.er_tema as Tema, ste.er_subtema as Subtema, es.es_especialidad as Area, la.riesgo_materializado as Rmaterializado, la.riesgo_materializado_identi as RmaterializadoIden"
			+ " , la.salio_bien , la.salio_mal , la.causa_raiz , la.que_aprendio , la.descriptor, la.accion_desarrollar, u.fullname as Responsable, la.ht_fechaplaneadacierre as Fecha , la.gap as Gap, la.ht_fechacierre as Fechacierre, la.ht_observacionesacta as observaciones"
			+ " ,IF(x.hojas_ht_id = la.ht_id, 'Si','No') as Seguimiento, IF(seg.htseg_evidencia1 IS NOT NULL, 'Si', 'No') as Evidencia "
			+ " FROM er_hoja_trabajo la"
			+ " INNER JOIN er_encabezado en ON (la.er_encabezado_id = en.er_encabezado_id AND la.er_encabezado_id IN :encabezado)"
			+ " INNER JOIN er_tipo_estudio ti ON (en.er_tipoestudio_id = ti.er_tipoestudio_id AND en.er_tipoestudio_id = 6)"
			+ " INNER JOIN er_estados est ON (la.er_estado_id = est.er_estado_id)"
			+ " INNER JOIN er_oportunidad op ON (la.er_oportunidad_id = op.er_oportunidad_id)"
			+ " LEFT OUTER JOIN er_cierre ci ON (la.er_cierre_id = ci.er_cierre_id)"
			+ " INNER JOIN er_tema te ON (la.er_tema_id = te.er_tema_id)"
			+ " INNER JOIN er_subtema ste ON (la.er_subtema_id = ste.er_subtema_id)"
			+ " INNER JOIN er_especialidad es ON (la.es_especialidad_id = es.es_especialidad_id )"
			+ " INNER JOIN users u ON (la.ht_responsableimplementacion = u.id AND la.ht_responsableimplementacion = COALESCE( :userId ,u.id))"
			+ " LEFT OUTER JOIN er_hojas_x_er_seguimiento x ON (la.ht_id = x.hojas_ht_id)"
			+ " LEFT OUTER JOIN er_hoja_seguimiento seg ON (x.seguimientos_htseg_id = seg.htseg_id)"
			+ " group by la.ht_id", nativeQuery=true)
	List<er_HojaTrabajoLeccionesAProjection> findHojaTrabajoLAProjectionByEncabezado(@Param("encabezado") List<Integer> encabeList,@Param("userId") Integer userId);
	
	//Lecciones aprendidas por user Id
	@Query(value="SELECT la.ht_id as Id, ti.er_tipoestudio as Tipo, en.e_nombreproyecto as Nompro, en.e_codigodocumento as Coddoc, est.er_estado as Estado, op.er_oportunidad as Oportunidad, ci.er_cierre as Cierre, la.nombre, la.resumen, te.er_tema as Tema, ste.er_subtema as Subtema, es.es_especialidad as Area, la.riesgo_materializado as Rmaterializado, la.riesgo_materializado_identi as RmaterializadoIden"
			+ " , la.salio_bien , la.salio_mal , la.causa_raiz , la.que_aprendio , la.descriptor, la.accion_desarrollar, u.fullname as Responsable, la.ht_fechaplaneadacierre as Fecha , la.gap as Gap, la.ht_fechacierre as Fechacierre, la.ht_observacionesacta as observaciones"
			+ " ,IF(x.hojas_ht_id = la.ht_id, 'Si','No') as Seguimiento, IF(seg.htseg_evidencia1 IS NOT NULL, 'Si', 'No') as Evidencia "
			+ " FROM er_hoja_trabajo la"
			+ " INNER JOIN er_encabezado en ON (la.er_encabezado_id = en.er_encabezado_id)"
			+ " INNER JOIN er_tipo_estudio ti ON (en.er_tipoestudio_id = ti.er_tipoestudio_id AND en.er_tipoestudio_id = 6)"
			+ " INNER JOIN er_estados est ON (la.er_estado_id = est.er_estado_id AND est.er_estado_id = 1)"
			+ " INNER JOIN er_oportunidad op ON (la.er_oportunidad_id = op.er_oportunidad_id)"
			+ " LEFT OUTER JOIN er_cierre ci ON (la.er_cierre_id = ci.er_cierre_id)"
			+ " INNER JOIN er_tema te ON (la.er_tema_id = te.er_tema_id)"
			+ " INNER JOIN er_subtema ste ON (la.er_subtema_id = ste.er_subtema_id)"
			+ " INNER JOIN er_especialidad es ON (la.es_especialidad_id = es.es_especialidad_id )"
			+ " INNER JOIN users u ON (la.ht_responsableimplementacion = u.id AND la.ht_responsableimplementacion = COALESCE( :userId ,u.id))"
			+ " LEFT OUTER JOIN er_hojas_x_er_seguimiento x ON (la.ht_id = x.hojas_ht_id)"
			+ " LEFT OUTER JOIN er_hoja_seguimiento seg ON (x.seguimientos_htseg_id = seg.htseg_id)"
			+ " group by la.ht_id", nativeQuery=true)
	List<er_HojaTrabajoLeccionesAProjection> findHojaTrabajoLAProjectionByUserId(@Param("userId") Integer userId);
	//Riesgo Materializado
	@Query(value="SELECT rm.ht_id as Id, ti.er_tipoestudio as Tipo, en.e_nombreproyecto as Nompro, en.e_codigodocumento as Coddoc, est.er_estado as Estado, op.er_oportunidad as Oportunidad, ci.er_cierre as Cierre, rm.descripcionrm as DescripcionRM, rm.riesgo_materializado_identi as RmaterializadoIden , rm.causarm, rm.consecuenciarm , rm.impactocosto ,  rm.impactotiempo , es.es_especialidad as Area"
			+ " , rm.descriptor, rm.accion_desarrollar, u.fullname as Responsable, rm.ht_fechaplaneadacierre as Fecha , rm.gap as Gap, rm.ht_fechacierre as Fechacierre, rm.ht_observacionesacta as observaciones"
			+ " ,IF(x.hojas_ht_id = rm.ht_id, 'Si','No') as Seguimiento, IF(seg.htseg_evidencia1 IS NOT NULL, 'Si', 'No') as Evidencia "
			+ " FROM er_hoja_trabajo rm"
			+ " INNER JOIN er_encabezado en ON (rm.er_encabezado_id = en.er_encabezado_id AND rm.er_encabezado_id IN :encabezado)"
			+ " INNER JOIN er_tipo_estudio ti ON (en.er_tipoestudio_id = ti.er_tipoestudio_id AND en.er_tipoestudio_id = 7)"
			+ " INNER JOIN er_estados est ON (rm.er_estado_id = est.er_estado_id)"
			+ " INNER JOIN er_oportunidad op ON (rm.er_oportunidad_id = op.er_oportunidad_id)"
			+ " LEFT OUTER JOIN er_cierre ci ON (rm.er_cierre_id = ci.er_cierre_id)"
			+ " INNER JOIN er_especialidad es ON (rm.es_especialidad_id = es.es_especialidad_id )"
			+ " INNER JOIN users u ON (rm.ht_responsableimplementacion = u.id AND rm.ht_responsableimplementacion = COALESCE( :userId ,u.id))"
			+ " LEFT OUTER JOIN er_hojas_x_er_seguimiento x ON (rm.ht_id = x.hojas_ht_id)"
			+ " LEFT OUTER JOIN er_hoja_seguimiento seg ON (x.seguimientos_htseg_id = seg.htseg_id)"
			+ " group by rm.ht_id", nativeQuery=true)
	List<er_HojaTrabajoRMaterializadoProjection> findHojaTrabajoRMProjectionByEncabezado(@Param("encabezado") List<Integer> encabeList,@Param("userId") Integer userId);
	
	//Riesgo Emergente
	@Query(value="SELECT e.ht_id as id , te.er_tipoestudio as tipo, en.e_nombreproyecto as nompro , en.e_codigodocumento as coddoc, es.er_estado as estado, op.er_oportunidad as oportunidad, ci.er_cierre as cierre, e.ht_evento as evento, ep.es_especialidad as especialidad, ma.er_matriz as ram,"
		+	" rac.rc as raconsecuencia, rap.rp as raprobabilidad, rav.rv as ravaloracion, e.ht_recomendacion as control, rec.rc as reconsecuencia,  rep.rp as reprobabilidad, rev.rv as revaloracion, u.fullname as responsable, e.gap as gap, e.ht_fechaplaneadacierre as fechaplaneada, e.ht_fechacierre as fechacierre, e.ht_observacionesacta as observacion"
		+	" ,IF(x.hojas_ht_id = e.ht_id, 'Si','No') as Seguimiento, IF(seg.htseg_evidencia1 IS NOT NULL, 'Si', 'No') as Evidencia "
		+ 	" FROM er_hoja_trabajo e"
		+	" INNER JOIN er_encabezado en ON (e.er_encabezado_id = en.er_encabezado_id AND e.er_encabezado_id IN :id_encabeList)"
		+	" INNER JOIN er_tipo_estudio te ON (en.er_tipoestudio_id = te.er_tipoestudio_id AND en.er_tipoestudio_id = 5)"
		+	" LEFT OUTER JOIN er_estados es ON e.er_estado_id = es.er_estado_id"
		+	" LEFT OUTER JOIN er_oportunidad op ON e.er_oportunidad_id = op.er_oportunidad_id"
		+ 	" LEFT OUTER JOIN er_cierre ci ON (e.er_cierre_id = ci.er_cierre_id)"
		+	" LEFT OUTER JOIN er_especialidad ep ON e.es_especialidad_id = ep.es_especialidad_id"
		+	" LEFT OUTER JOIN er_matrizram ma ON e.er_matrizram_id = ma.er_matriz_id"
		+	" LEFT OUTER JOIN er_riesgos_consecuencias rac ON e.ra_rc_id = rac.rc_id"
		+	" LEFT OUTER JOIN er_riesgos_probabilidad rap ON e.ra_rp_id = rap.rp_id"
		+	" LEFT OUTER JOIN er_riesgos_valoracion rav ON (e.ra_rv_id = rav.rv_id AND rav.rv_id != 1)"
		+	" LEFT OUTER JOIN er_riesgos_consecuencias rec ON e.rr_rc_id = rec.rc_id"
		+	" LEFT OUTER JOIN er_riesgos_probabilidad rep ON e.rr_rp_id = rep.rp_id"
		+	" LEFT OUTER JOIN er_riesgos_valoracion rev ON e.rr_rv_id = rev.rv_id"
		+	" INNER JOIN users u ON (e.ht_responsableimplementacion = u.id AND e.ht_responsableimplementacion = COALESCE( :userId ,u.id))"
		+ 	" LEFT OUTER JOIN er_hojas_x_er_seguimiento x ON (e.ht_id = x.hojas_ht_id)"
		+ 	" LEFT OUTER JOIN er_hoja_seguimiento seg ON (x.seguimientos_htseg_id = seg.htseg_id)"
		+	 " group by e.ht_id" , nativeQuery=true)
	List<er_HojaTrabajoREmergenteProjection> findHojaTrabajoREProjectionByEncabezado(@Param("id_encabeList") List<Integer> id_encabeList,@Param("userId") Integer userId);
	
}
