package com.example.demo.repository;

import com.example.demo.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;  //!!!

import java.util.List;
import java.util.Optional;

// jparepository > pageandsortrepository > crudrepository > repository
public interface CountryRepository extends JpaRepository<Country, String> {

    Country findByName(String name);
    List<Country> findAllByName(String name);
    Optional<Country> findById(String id);
    //List<Country> findAllById(String id, Pageable pageable);
    Page<Country> findById(String id, Pageable pageable);
    Page<Country> findAll(Pageable pageable);
    List<Country> findCountriesByName(String name);

}
