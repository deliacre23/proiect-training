package com.example.demo.service;

import com.example.demo.entity.Country;
import com.example.demo.entity.User;
import com.example.demo.repository.CountryRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CountryRepository countryRepository;


    public String addCountryToUser( Integer user_id, Integer country_id)

    {
        Country country = countryRepository.findById(country_id).orElse(null);
        User user = userRepository.findById(Long.valueOf(user_id)).orElse(null);
        Set<Country> countries = user.getCountries();
        countries.add(country);
        user.setCountries(countries);
        userRepository.save(user); //!!!

        return user.toString();
    }
}
