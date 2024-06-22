package com.tpe.repository;


import com.tpe.entity.concretes.user.Role;
import com.tpe.entity.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("SELECT r FROM Role r WHERE r.roleType = ?1")
    Optional<Role> findByEnumRoleEquals(RoleType roleType);
}