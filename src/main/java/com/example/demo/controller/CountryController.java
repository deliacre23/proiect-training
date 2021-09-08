package com.example.demo.controller;


import com.example.demo.entity.Country;
import com.example.demo.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CountryController {

    @Autowired
    private CountryService service;

    @PostMapping("/addCountry")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Country addCountry(@RequestBody Country country)
    {
        return service.saveCountry(country);
    }

    @PostMapping("/addCountries")
    public List<Country> addCountries(@RequestBody List<Country> countries)
    {
        return service.saveCountries(countries);
    }

    @GetMapping("/countries")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Country> findAllCountries()
    {
        return service.getCountries();
    }

    @GetMapping("/product/{id}")
    public Country findCountryById(@PathVariable int id)
    {
        return service.getCountryById(id);
    }

    @GetMapping("/product/{name}")
    public Country findCountryByName(@PathVariable String name)
    {
        return service.getCountryByName(name);
    }

    @PutMapping("/update")
    public Country updateCountry(@RequestBody Country country)
    {
        return service.updateCountry(country);
    }

    @DeleteMapping("/delete{id}")
    public String deleteCountry(@PathVariable int id)
    {
        return service.deleteCountry(id);
    }


}
