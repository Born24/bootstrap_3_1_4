package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    void add(User user);

    void update(User user);

    void delete(Long id);

    User findByUsername(String username);

}
