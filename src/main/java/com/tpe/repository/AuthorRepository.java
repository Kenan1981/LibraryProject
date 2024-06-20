package com.tpe.repository;

import com.tpe.entity.concretes.business.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    boolean existsAuthorByNameEqualsIgnoreCase(String name);
}
