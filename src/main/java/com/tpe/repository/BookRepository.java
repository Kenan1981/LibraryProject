package com.tpe.repository;

import com.tpe.entity.concretes.business.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BookRepository extends JpaRepository<Book, Long> {


    Optional<Book> findById(Long id);

    boolean existsByNameIgnoreCase(String name);

    void deleteById(Long id); // delete


    boolean existsByCategory_Id(Long categoryId);


}