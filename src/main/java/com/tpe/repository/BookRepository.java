package com.tpe.repository;

import com.tpe.entity.concretes.business.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {



}