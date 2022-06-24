package com.ecopetrol.ECOrisk.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecopetrol.ECOrisk.Models.erInfoGeneralTalleres;

@Repository
public interface erInfoGeneralTalleresRepository extends JpaRepository<erInfoGeneralTalleres, Integer> {

	@Query(value="select * from er_infogeneraldetalleres ei where ei.id = :id", nativeQuery=true)
	erInfoGeneralTalleres findByinfoGeneralTalleresId(@Param("id") Integer id);
	

	
	
}
