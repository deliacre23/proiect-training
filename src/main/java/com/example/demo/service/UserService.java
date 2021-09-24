package com.example.demo.service;

import com.example.demo.entity.Country;
import com.example.demo.entity.ERole;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.CountryRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements InitializingBean {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CountryRepository countryRepository;

    public Set<Country> getUserCountries(Long id) {
        User user = this.userRepository.findById(id).orElse(null);
        return user.getCountries();
    }

//    public Set<Country> getModCountries(Long id) {
//        User user = this.userRepository.findById(id).orElse(null);
//        return user.getCountries();
//    }
//
    public User addCountryToUser( Integer user_id, String country_id)

    {
        Country country = countryRepository.findById(country_id).orElse(null);
        User user = userRepository.findById(Long.valueOf(user_id)).orElse(null);
        if(user.getRoles().stream().filter(u -> u.getName().equals(ERole.ROLE_MODERATOR)).toList().size() != 0
                || (user.getRoles().stream().filter(u -> u.getName().equals(ERole.ROLE_USER)).toList().size() != 0
                     && user.getCountries().size() == 0)){
            Set<Country> countries = user.getCountries();
            countries.add(country);
            user.setCountries(countries);
            userRepository.save(user); // !!!
        }
        return user;
    }


    public User deleteCountryFromUser(Long user_id, String country_id) {
        Country country = countryRepository.findById(country_id).orElse(null);
        User user = userRepository.findById(user_id).orElse(null);
        user.getCountries().remove(country);
        userRepository.save(user);
        return user;
    }

    public Page<User> getUsersPage(Pageable pageable) {

       // return (Page<User>) userRepository.findAll(pageable).stream().filter(user -> user.getRoles().contains(new Role(2, ERole.ROLE_ADMIN))).collect(Collectors.toList());
        return userRepository.findAllByRolesIsNot(pageable, new Role(2, ERole.ROLE_ADMIN));
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.roleRepository.count() == 0) {
            try {
                Role[] roles = new Role[]{new Role(ERole.ROLE_USER),new Role(ERole.ROLE_ADMIN),new Role(ERole.ROLE_MODERATOR)};
                this.roleRepository.saveAll(Arrays.asList(roles));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username).orElse(null);
    }


    public User makeMod(Long id) {
//        System.out.println(id+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(3, ERole.ROLE_MODERATOR));
        User user = this.userRepository.findById(id).orElse(null);
        user.setRoles(roles);
//        System.out.println(user.getRoles().toString()+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        userRepository.save(user);
        return user;
    }

    public User makeUser(Long id) {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1, ERole.ROLE_USER));
        User user = this.userRepository.findById(id).orElse(null);
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }

}
