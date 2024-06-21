package com.tpe.repository;

import com.tpe.entity.concretes.user.User;
import com.tpe.entity.enums.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.role.name = :name")
    Page<User> findByUserByRole(String name, Pageable pageable);


    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);

    @Query(value = "SELECT COUNT(u) FROM User u WHERE u.role.roleType = ?1")
    int countAdmin(RoleType roleType);


//    @Query("SELECT u FROM User u ORDER BY u.borrowCount DESC")
//    List<User> findMostBorrowers(Pageable pageable);
}