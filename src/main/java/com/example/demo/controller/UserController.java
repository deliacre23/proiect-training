package com.example.demo.controller;

import com.example.demo.entity.Country;
import com.example.demo.entity.User;
import com.example.demo.service.CountryService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CountryService countryService;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
//    public Iterable<Country> adminAccess() {
//        return this.countryService.getCountries();
//    }
    public String adminAccess(){
        return "Admin board";
    }

    @GetMapping("/countrieslist")
    @PreAuthorize("hasRole('ADMIN')")
    public Iterable<Country> adminCountries() {
        return this.countryService.getCountries();
    }

    //http://localhost:8080/test/api/addCountryToUser?user_id=2&country_id=1
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/addCountryToUser/{user_id}/{country_id}")
    public User addCountryToUser(@PathVariable Integer user_id, @PathVariable String country_id)
        {return userService.addCountryToUser(user_id, country_id);}

    @GetMapping("/deleteCountryFromUser/{user_id}/{country_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User deleteCountryFromUser(@PathVariable Long user_id, @PathVariable String country_id){
        return userService.deleteCountryFromUser(user_id, country_id);
    }

    @GetMapping("/countries/{page}")
    @PreAuthorize(" hasRole('ADMIN')")
    public List<Country> getCountriesPage(@PathVariable int page){

        Pageable paging = PageRequest.of(page, 10);
        return this.countryService.getCountriesPage(paging).getContent();
    }

    @GetMapping("/users/{page}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<User> getUserPage(@PathVariable int page){

        Pageable paging = PageRequest.of(page, 10);
        return this.userService.getUsersPage(paging).getContent();
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public User getUser(@PathVariable String username){

        return this.userService.getUserByUsername(username);
    }

    @GetMapping("/countriesByName/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Country> getCountriesByName(@PathVariable String name) {
        return this.countryService.findCountriesByName(name);
    }

    @GetMapping("/countriesContaining/{term}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Country> getCountriesContaining(@PathVariable String term) {
        return this.countryService.findCountriesContaining(term);
    }


    @GetMapping("/userCountries/{id}")
    @PreAuthorize("hasRole('USER')")
    public Set<Country> getUserCountries(@PathVariable Long id){
        return this.userService.getUserCountries(id);
    }

    @GetMapping("/modCountries/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public Set<Country> getModCountries(@PathVariable Long id){
        return this.userService.getUserCountries(id);
    }

    @GetMapping("/makeMod/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User makeMod(@PathVariable Long id){
        return this.userService.makeMod(id);
    }

    @GetMapping("/makeUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User makeUser(@PathVariable Long id){
        return this.userService.makeUser(id);
    }




}
