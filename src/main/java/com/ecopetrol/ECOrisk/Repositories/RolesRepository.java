package com.ecopetrol.ECOrisk.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecopetrol.ECOrisk.Models.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {

}
