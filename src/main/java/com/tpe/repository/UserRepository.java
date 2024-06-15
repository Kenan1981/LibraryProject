package com.tpe.repository;

import com.tpe.entity.concretes.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String username);
}
