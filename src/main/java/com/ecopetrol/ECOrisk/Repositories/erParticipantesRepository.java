package com.ecopetrol.ECOrisk.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecopetrol.ECOrisk.Models.erParticipantes;
import com.ecopetrol.ECOrisk.Projections.erParticipantesProjection;

@Repository
public interface erParticipantesRepository extends JpaRepository<erParticipantes, Integer> {

	@Query(value="SELECT u.fullname as Nombre, par.er_rol as rol , par.er_dependencia as dependencia , u.username as correo FROM er_participantes par"
			+ " INNER JOIN users u ON u.id = par.er_participante"
			+ " INNER JOIN er_encabezado en ON en.er_encabezado_id = par.er_encabezado_id AND en.er_encabezado_id = :encabezado ", nativeQuery=true)
	List<erParticipantesProjection> findPartiByEncabeIdProjection(@Param("encabezado") Integer encabezado);
}
