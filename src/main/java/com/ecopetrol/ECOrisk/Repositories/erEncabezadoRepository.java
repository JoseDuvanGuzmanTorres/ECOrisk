package com.ecopetrol.ECOrisk.Repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ecopetrol.ECOrisk.Models.erEncabezado;
import com.ecopetrol.ECOrisk.Projections.ContadorTalleresProjection;
import com.ecopetrol.ECOrisk.Projections.erEncabezadoProjection;

@Repository
public interface erEncabezadoRepository extends JpaRepository<erEncabezado, Integer> {

	@Query(value="select * from er_encabezado e where e.e_codigodocumento = :CodDoc LIMIT 1", nativeQuery=true)
    erEncabezado findByCodDoc(@Param("CodDoc") String CodDoc);

	@Query(value="select e.* from er_encabezado e, er_hoja_trabajo ht WHERE e.er_encabezado_id = ht.er_encabezado_id and ht.ht_responsableimplementacion = :user_id group by er_encabezado_id" , nativeQuery=true)
	List<erEncabezado> findAllByResponsable(@Param("user_id") Integer user_id);
	
	@Query(value="select e.* from er_encabezado e WHERE e.er_encabezado_id = :encabezado_id LIMIT 1" , nativeQuery=true)
	erEncabezado findByEncabezado_Id(@Param("encabezado_id") Integer encabezado_id);
	
	@Query(value="select e.* from er_encabezado e, er_proyecto pr WHERE e.er_proyecto_id = pr.er_proyecto_id AND pr.er_proyecto_id = :ProId" , nativeQuery=true)
	List<erEncabezado> findByProId(@Param("ProId") Integer ProId);
	
	@Query(value="select e.e_liderproyecto from er_encabezado e, er_proyecto pr WHERE e.er_proyecto_id = pr.er_proyecto_id AND pr.er_proyecto_id = :ProId GROUP BY e.e_liderproyecto" , nativeQuery=true)
	List<String> findByProIdYDueno(@Param("ProId") Integer ProId);
	
	@Query(value="select e.e_liderproyecto from er_encabezado e, er_proyecto pr WHERE e.er_proyecto_id = pr.er_proyecto_id GROUP BY e.e_liderproyecto" , nativeQuery=true)
	List<String> findByAllDueno();
	
	@Query(value="select e.* from er_encabezado en WHERE en.e_liderproyecto = :DuenoId" , nativeQuery=true)
	List<erEncabezado> findEncabeByDueno(@Param("DuenoId") Integer DuenoId);
	
	@Query(value="select en.* from er_encabezado en where en.er_encabezado_id IN :id_encabeList" , nativeQuery=true)
	List<erEncabezado> findEncabeByList(@Param("id_encabeList") List<Integer> id_encabeList);
	
	//Projections

	@Query(value="select e.er_encabezado_id as id, te.er_tipoestudio as tipo, ce.er_proceso as proceso, pro.er_proyecto as codpro, e.e_codigodocumento as coddoc, e.e_nombreproyecto as nompro, e.e_objetivoproyecto as obj, et.ee_etapaproyecto as etapa, er.er_regionalgsc as regional, eg.eg_gerenciacliente as gerencia, u1.fullname as dueno, ed.ed_deptocliente as depar, u2.fullname as lider, e.e_instalacioncliente as instala, e.e_fechataller as fechatal, e.aud_fechacreacion as fechacarga"
			+ " FROM er_encabezado e"
			+ " INNER JOIN er_encabezado e2 ON ( e.er_encabezado_id = e2.er_encabezado_id AND e.e_codigodocumento LIKE %:codigo% AND e.e_nombreproyecto LIKE %:nombre% AND e.e_objetivoproyecto LIKE %:objetivo%  AND e.e_instalacioncliente LIKE %:instalacion% AND (e.e_fechataller BETWEEN :fechainicial AND :fechafinal))"
			+ " INNER JOIN er_tipo_estudio te ON (te.er_tipoestudio_id = e.er_tipoestudio_id AND te.er_tipoestudio_id = COALESCE( :tipoestudio ,e.er_tipoestudio_id))"
			+ " INNER JOIN er_proceso ce ON (ce.er_proceso_id = e.er_proceso_id AND ce.er_proceso_id = COALESCE( :proceso ,e.er_proceso_id))"
			+ " INNER JOIN  er_proyecto pro ON (pro.er_proyecto_id = e.er_proyecto_id AND pro.er_proyecto LIKE %:proyecto%)"
			+ " INNER JOIN  er_etapas et ON (et.ee_etapaproyecto_id = e.ee_etapaproyecto_id AND et.ee_etapaproyecto_id = COALESCE( :etapa ,e.ee_etapaproyecto_id))"
			+ " INNER JOIN er_regionales er ON (er.er_regionalgsc_id = e.er_regionalgsc_id AND er.er_regionalgsc_id = COALESCE( :regional ,e.er_regionalgsc_id))"
			+ " INNER JOIN er_gerencias eg ON (eg.eg_gerenciacliente_id = e.eg_gerenciacliente_id AND eg.eg_gerenciacliente_id = COALESCE( :gerencia ,e.eg_gerenciacliente_id))"
			+ " INNER JOIN users u1 ON (u1.id = e.e_liderproyecto AND u1.fullname LIKE %:dueno%)"
			+ " INNER JOIN er_departamentos ed ON (ed.ed_deptocliente_id = e.ed_deptocliente_id AND ed.ed_deptocliente_id = COALESCE( :departamento ,e.ed_deptocliente_id))"
			+ " LEFT OUTER JOIN er_departamentos ed2 ON (ed2.ed_deptocliente_id = e.ed_deptocliente_id2)"
			+ " LEFT OUTER JOIN er_departamentos ed3 ON (ed3.ed_deptocliente_id = e.ed_deptocliente_id3)"
			+ " INNER JOIN users u2 ON (u2.id = e.e_lidertaller AND u2.fullname LIKE %:lider%)"
			+ " INNER JOIN er_hoja_trabajo ht ON ht.er_encabezado_id = e.er_encabezado_id"
			+ " LEFT OUTER JOIN users u3 ON (u3.id = ht.ht_responsableimplementacion  AND u3.id = COALESCE( :user_id ,ht.ht_responsableimplementacion) )"
			+ " GROUP BY  e.er_encabezado_id", nativeQuery=true)
	List<erEncabezadoProjection> findByMultipleFiltersAndUserProjection(@Param("tipoestudio") String tipoestudio, @Param("proceso") String proceso, @Param("proyecto") String proyecto, @Param("codigo") String codigo, @Param("nombre") String nombre,@Param("objetivo") String objetivo,@Param("etapa") String etapa,@Param("regional") String regional,
			@Param("gerencia") String gerencia,@Param("dueno") String dueno, @Param("departamento") String departamento, @Param("lider") String lider, @Param("instalacion") String instalacion,@Param("fechainicial") Date fechainicial, @Param("fechafinal") Date fechafinal, @Param("user_id") Integer user_id);

	//query para duenos de talleres	
	@Query(value="select e.er_encabezado_id as id, te.er_tipoestudio as tipo, ce.er_proceso as proceso, pro.er_proyecto as codpro, e.e_codigodocumento as coddoc, e.e_nombreproyecto as nompro, e.e_objetivoproyecto as obj, et.ee_etapaproyecto as etapa, er.er_regionalgsc as regional, eg.eg_gerenciacliente as gerencia, u1.fullname as dueno, ed.ed_deptocliente as depar, u2.fullname as lider, e.e_instalacioncliente as instala, e.e_fechataller as fechatal, e.aud_fechacreacion as fechacarga"
			+ " FROM er_encabezado e"
			+ " INNER JOIN er_encabezado e2 ON ( e.er_encabezado_id = e2.er_encabezado_id AND e.e_codigodocumento LIKE %:codigo% AND e.e_nombreproyecto LIKE %:nombre% AND e.e_objetivoproyecto LIKE %:objetivo%  AND e.e_instalacioncliente LIKE %:instalacion% AND (e.e_fechataller BETWEEN :fechainicial AND :fechafinal))"
			+ " INNER JOIN er_tipo_estudio te ON (te.er_tipoestudio_id = e.er_tipoestudio_id AND te.er_tipoestudio_id = COALESCE( :tipoestudio ,e.er_tipoestudio_id))"
			+ " INNER JOIN er_proceso ce ON (ce.er_proceso_id = e.er_proceso_id AND ce.er_proceso_id = COALESCE( :proceso ,e.er_proceso_id))"
			+ " INNER JOIN  er_proyecto pro ON (pro.er_proyecto_id = e.er_proyecto_id AND pro.er_proyecto LIKE %:proyecto%)"
			+ " INNER JOIN  er_etapas et ON (et.ee_etapaproyecto_id = e.ee_etapaproyecto_id AND et.ee_etapaproyecto_id = COALESCE( :etapa ,e.ee_etapaproyecto_id))"
			+ " INNER JOIN er_regionales er ON (er.er_regionalgsc_id = e.er_regionalgsc_id AND er.er_regionalgsc_id = COALESCE( :regional ,e.er_regionalgsc_id))"
			+ " INNER JOIN er_gerencias eg ON (eg.eg_gerenciacliente_id = e.eg_gerenciacliente_id AND eg.eg_gerenciacliente_id = COALESCE( :gerencia ,e.eg_gerenciacliente_id))"
			+ " INNER JOIN users u1 ON (u1.id = e.e_liderproyecto AND u1.fullname LIKE %:dueno%)"
			+ " INNER JOIN er_departamentos ed ON (ed.ed_deptocliente_id = e.ed_deptocliente_id AND ed.ed_deptocliente_id = COALESCE( :departamento ,e.ed_deptocliente_id))"
			+ " LEFT OUTER JOIN er_departamentos ed2 ON (ed2.ed_deptocliente_id = e.ed_deptocliente_id2)"
			+ " LEFT OUTER JOIN er_departamentos ed3 ON (ed3.ed_deptocliente_id = e.ed_deptocliente_id3)"
			+ " INNER JOIN users u2 ON (u2.id = e.e_lidertaller AND u2.fullname LIKE %:lider%)"
			+ " INNER JOIN er_hoja_trabajo ht ON ht.er_encabezado_id = e.er_encabezado_id"
			+ " INNER JOIN users u3 ON (u3.id = ht.ht_responsableimplementacion  AND u3.fullname LIKE %:responsable% AND u3.id = COALESCE( :user_id ,ht.ht_responsableimplementacion))"
			+ " GROUP BY  e.er_encabezado_id", nativeQuery=true)
	List<erEncabezadoProjection> findByMultipleFiltersAndUserProjectionResponsableT(@Param("tipoestudio") String tipoestudio, @Param("proceso") String proceso, @Param("proyecto") String proyecto, @Param("codigo") String codigo, @Param("nombre") String nombre,@Param("objetivo") String objetivo,@Param("etapa") String etapa,@Param("regional") String regional,
			@Param("gerencia") String gerencia,@Param("dueno") String dueno,@Param ("responsable") String responsable, @Param("departamento") String departamento, @Param("lider") String lider, @Param("instalacion") String instalacion,@Param("fechainicial") Date fechainicial, @Param("fechafinal") Date fechafinal, @Param("user_id") Integer user_id);
	
	

	@Query(value="SELECT SUM(CASE WHEN en.er_tipoestudio_id = 1 AND en.er_proceso_id = 1 THEN 1 ELSE 0 END) as Whatifca,"
			+ " SUM(CASE WHEN en.er_tipoestudio_id = 1 AND en.er_proceso_id = 2 THEN 1 ELSE 0 END) as Whatifop,"
			+ " SUM(CASE WHEN en.er_tipoestudio_id = 2 AND en.er_proceso_id = 1 THEN 1 ELSE 0 END) as Construca,"
			+ " SUM(CASE WHEN en.er_tipoestudio_id = 2 AND en.er_proceso_id = 2 THEN 1 ELSE 0 END) as Construop,"
			+ " SUM(CASE WHEN en.er_tipoestudio_id = 3 AND en.er_proceso_id = 1 THEN 1 ELSE 0 END) as Desreca,"
			+ " SUM(CASE WHEN en.er_tipoestudio_id = 3 AND en.er_proceso_id = 2 THEN 1 ELSE 0 END) as Desreop,"
			+ " SUM(CASE WHEN en.er_tipoestudio_id = 4 AND en.er_proceso_id = 1 THEN 1 ELSE 0 END) as Peerreca,"
			+ " SUM(CASE WHEN en.er_tipoestudio_id = 4 AND en.er_proceso_id = 2 THEN 1 ELSE 0 END) as Peerreop, "
			+ " SUM(CASE WHEN en.er_tipoestudio_id = 6 AND en.er_proceso_id = 1 THEN 1 ELSE 0 END) as LeccionesAca, "
			+ " SUM(CASE WHEN en.er_tipoestudio_id = 6 AND en.er_proceso_id = 2 THEN 1 ELSE 0 END) as LeccionesAop "
			+ " FROM er_encabezado en", nativeQuery=true)
	ContadorTalleresProjection findByContadoresTalleres();
}
