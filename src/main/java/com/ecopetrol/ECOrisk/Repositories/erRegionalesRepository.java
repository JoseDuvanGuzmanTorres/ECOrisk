package com.ecopetrol.ECOrisk.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ecopetrol.ECOrisk.Models.erRegionales;

@Repository
public interface erRegionalesRepository extends JpaRepository<erRegionales, Integer> {
	
	@Query(value="select * from er_regionales e where e.er_regionalgsc = :regional LIMIT 1", nativeQuery=true)
    erRegionales findByRegional(@Param("regional") String regional);
	
	@Query(value="select * from er_regionales ORDER BY er_regionalgsc ASC", nativeQuery=true)
	List<erRegionales> findByAllAsc();
}
