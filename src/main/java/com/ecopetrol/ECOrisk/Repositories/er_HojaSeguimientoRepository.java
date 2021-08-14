package com.ecopetrol.ECOrisk.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ecopetrol.ECOrisk.Models.er_HojaSeguimiento;
import com.ecopetrol.ECOrisk.Projections.er_HojaSeguimientoProjection;

@Repository
public interface er_HojaSeguimientoRepository extends JpaRepository<er_HojaSeguimiento, Integer> {
	/*
	@Query(value="select e.* from er_hoja_seguimiento e, er_hoja_trabajo ho where ho.ht_id = e.ht_id AND e.ht_id = :hojatrabajo_id" , nativeQuery=true)
	List<er_HojaSeguimiento> findByHojaTrabajoId(@Param("hojatrabajo_id") Integer hojatrabajo_id);
	*/
	
	@Query(value="SELECT seg.htseg_id as id, seg.htseg_comentario as comentario, seg.htseg_fechaescrita as fecha, seg.htseg_ruta as ruta, seg.htseg_evidencia1 as archi1, seg.htseg_evidencia2 as archi2, seg.htseg_evidencia3 as archi3, u.fullname as usuario from er_hoja_seguimiento seg, er_hoja_trabajo ho, er_hojas_x_er_seguimiento x, users u where ho.ht_id = x.hojas_ht_id AND seg.htseg_id = x.seguimientos_htseg_id AND seg.user_id = u.id AND ho.ht_id = :hojatrabajo_id" , nativeQuery=true)
	List<er_HojaSeguimientoProjection> findByHojaTrabajoIdProjection(@Param("hojatrabajo_id") Integer hojatrabajo_id);
}
