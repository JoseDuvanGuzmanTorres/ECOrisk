package com.ecopetrol.ECOrisk.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecopetrol.ECOrisk.Models.Users;
import com.ecopetrol.ECOrisk.Projections.UsersProjection;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

	@Query(value="select * from users e where e.fullname LIKE %:fullname%", nativeQuery=true)
    List<Users> findByFullname(@Param("fullname") String fullname);
	
	@Query(value="select * from users e where e.username = :username LIMIT 1", nativeQuery=true)
    Users findByUsername(@Param("username") String username);
	
	@Query(value="select * from users e where e.id = :id", nativeQuery=true)
    Users findByUsersId(@Param("id") Integer id);
	
	@Query(value="SELECT u.* FROM users u INNER JOIN er_hoja_trabajo e ON (e.ht_responsableimplementacion = u.id AND e.er_estado_id = 1) GROUP BY u.id", nativeQuery=true)
    List<Users> findUsersByControlesAbiertos();
	
	@Query(value="SELECT u.* FROM users u INNER JOIN er_encabezado en ON (en.e_liderproyecto = u.id) GROUP BY u.id", nativeQuery=true)
    List<Users> findUsersByDueno();
	
	@Query(value="SELECT u.* FROM users u where roles_id = 1 GROUP BY u.id", nativeQuery=true)
    List<Users> findUsersByAllDueno();
	
	
	@Query(value="SELECT u.* FROM users u WHERE u.roles_id  = 2 OR u.roles_id  = 3", nativeQuery=true)
    List<Users> findUsersBySemiAdmin();
	
	@Query(value="SELECT u.id, u.fullname, u.username, r.rol FROM users u, roles r WHERE u.roles_id = r.id AND u.id != 105 AND u.id != 110", nativeQuery=true)
    List<UsersProjection> findUsersProjection();
	
	
	
	
}
