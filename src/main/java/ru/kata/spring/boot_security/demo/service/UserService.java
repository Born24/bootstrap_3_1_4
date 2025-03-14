package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> findAll();

    User findById(Long id);

    void add(User user);

    void update(User user);

    void delete(Long id);

    User findByUsername(String username);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    void addUserWithRoles(User user, List<Long> roleIds);


}
