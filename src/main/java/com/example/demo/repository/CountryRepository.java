package com.example.demo.repository;

import com.example.demo.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CountryRepository extends JpaRepository<Country, Integer> {

    Country findByName(String name);
    List<Country> findAllByName(String name);
}
