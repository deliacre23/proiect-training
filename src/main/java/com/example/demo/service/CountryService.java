package com.example.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.entity.Country;
import com.example.demo.repository.CountryRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
public class CountryService implements InitializingBean {

    @Autowired
    private CountryRepository repository;

    public Country saveCountry(Country country) {
        return repository.save(country);
    }

    public Iterable<Country> saveCountries(List<Country> countries) {
        return repository.saveAll(countries);
    }

    public Iterable<Country> getCountries() {
        return repository.findAll();
    }

    public Country getCountryById(String id) {
        return repository.findById(id).orElse(null);
    }

    public Country getCountryByName(String name) {
        return repository.findByName(name);
    }


    public List<Country> findCountriesContaining(String term) {return repository.findAll().stream()
            .filter(country -> country.getName().toLowerCase().contains(term.toLowerCase())).toList();}

    //listCarnet.stream().filter(carnet -> codeIsIn.equals(carnet.getCodeIsin())).findFirst().orElse(null);

    public String deleteCountry(String id) {
        repository.deleteById(id);
        return "removed" + id;
    }

    public Page<Country> getCountriesPage(Pageable pageable) { return repository.findAll(pageable);}

    public List<Country> findCountriesByName(String name) {return repository.findCountriesByName(name);}

    public Country updateCountry(Country country) {
        Country existingCountry = repository.findById(country.getId()).orElse(null);
        existingCountry.setName(country.getName());
        existingCountry.setId(country.getId());
        existingCountry.setFlag(country.getFlag());
        return repository.save(existingCountry);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.repository.count() == 0) {
            try {
                var json = Files.readString(Paths.get("src/main/java/com/example/demo/service/countries.json"));
                var mapper = new ObjectMapper();
                var countries = mapper.readValue(json, Country[].class);
                this.repository.saveAll(Arrays.asList(countries));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}
