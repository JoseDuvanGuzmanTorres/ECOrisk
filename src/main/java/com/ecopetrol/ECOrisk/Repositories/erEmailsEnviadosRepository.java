package com.ecopetrol.ECOrisk.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecopetrol.ECOrisk.Models.erEmailsEnviados;
import com.ecopetrol.ECOrisk.Projections.erEmailsEnviadosProjection;

@Repository
public interface erEmailsEnviadosRepository extends JpaRepository<erEmailsEnviados, Integer> {

	@Query(value="SELECT em.er_email_id as Id , em.asunto as Asunto, u.fullname as Usuario, em.fecha as Fecha FROM er_emails_enviados em , users u WHERE u.id = em.user_id", nativeQuery=true)
	List<erEmailsEnviadosProjection> findByAllEmailsProjection();
	
}
