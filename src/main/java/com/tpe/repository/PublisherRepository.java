package com.tpe.repository;


import com.tpe.entity.concretes.business.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    boolean existsByName(String name);

    boolean existsByNameEqualsIgnoreCase(String name);

}
