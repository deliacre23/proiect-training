package com.example.demo.controller;


import com.example.demo.entity.Country;
import com.example.demo.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
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
    public Iterable<Country> addCountries(@RequestBody List<Country> countries)
    {
        return service.saveCountries(countries);
    }
//
//    @GetMapping("/countries")
//    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
//    public Iterable<Country> findAllCountries()
//    {
//        return service.getCountries();
//    }

    @GetMapping("/country/{id}")
    public Country findCountryById(@PathVariable String id)
    {
        return service.getCountryById(id);
    }

    @GetMapping("/country/{name}")
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
    public String deleteCountry(@PathVariable String id)
    {
        return service.deleteCountry(id);
    }

    @GetMapping("/countries/{page}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Country> getCountriesPage(@PathVariable int page){

        Pageable paging = PageRequest.of(page, 10);
        return this.service.getCountriesPage(paging).getContent();
    }
}
