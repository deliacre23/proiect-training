package com.example.demo.service;

import com.example.demo.entity.Country;
import com.example.demo.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {
    @Autowired
    private CountryRepository repository;

    public Country saveCountry(Country country)
    {
        return repository.save(country);
    }

    public List<Country> saveCountries(List<Country> countries)
    {
        return repository.saveAll(countries);
    }

    public List<Country> getCountries()
    {
        return repository.findAll();
    }
    public Country getCountryById(int id)
    {
        return repository.findById(id).orElse(null);
    }
    public Country getCountryByName(String name)
    {
        return repository.findByName(name);
    }

    public String deleteCountry(int id)
    {
        repository.deleteById(id);
        return "removed" + id;
    }

    public Country updateCountry(Country country)
    {
        Country existingCountry=repository.findById(country.getId()).orElse(null);
        existingCountry.setName(country.getName());
        existingCountry.setCode(country.getCode());
        existingCountry.setFlag(country.getFlag());
        return repository.save(existingCountry);
    }

}
