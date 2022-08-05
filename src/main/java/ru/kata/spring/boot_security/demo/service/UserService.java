package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    User getCurrentUser();

    User getUserByUsername(String username);

    List<User> allUsers();

    void saveUser(User user);

    User findById(long id);

    void updateUser(User user);

    void deleteUser(Long Id);
}
