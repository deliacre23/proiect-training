package com.example.demo.controller;

import com.example.demo.entity.Country;
import com.example.demo.service.CountryService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class UserController {

    @Autowired
    private UserService userService;

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
    public String adminAccess() {
        return "Admin Board.";
    }

    //http://localhost:8080/test/api/addCountryToUser?user_id=2&country_id=1
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/addCountryToUser")
    public String addCountryToUser(Integer user_id, Integer country_id) {return userService.addCountryToUser(user_id, country_id);}
    //
    // String addCountryToUser()

}
