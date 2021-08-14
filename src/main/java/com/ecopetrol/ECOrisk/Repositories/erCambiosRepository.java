package com.ecopetrol.ECOrisk.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecopetrol.ECOrisk.Models.erCambios;
import com.ecopetrol.ECOrisk.Projections.erCambiosProjection;

@Repository
public interface erCambiosRepository extends JpaRepository<erCambios, Integer> {

	@Query(value="SELECT cam.er_cambio_id as id, seg.htseg_comentario as comentario, u1.fullname as viejo, u2.fullname as nuevo, cam.er_olddate as vieja, cam.er_newdate as nueva , seg.htseg_ruta as ruta, seg.htseg_evidencia1 as archi1, seg.htseg_evidencia2 as archi2, seg.htseg_evidencia3 as archi3, u3.fullname as responsable"
			+ " FROM er_hojas_x_er_seguimiento x"
			+ " INNER JOIN er_hoja_seguimiento seg ON seg.htseg_id = x.seguimientos_htseg_id"
			+ " INNER JOIN er_hoja_trabajo e ON (e.ht_id = x.hojas_ht_id AND e.ht_id = :hojatrabajo_id)"
			+ " INNER JOIN er_cambios cam ON cam.er_cambio_id = seg.er_cambio_id"
			+ " LEFT OUTER JOIN users u1 ON u1.id = cam.er_olduser"
			+ " LEFT OUTER JOIN users u2 ON u2.id = cam.er_newuser"
			+ " INNER JOIN users u3 ON seg.user_id = u3.id", nativeQuery=true)
	List<erCambiosProjection> findByHojaTrabajoIdProjection(@Param("hojatrabajo_id") Integer hojatrabajo_id);
	
}
