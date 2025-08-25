package com.darwinruiz.shoplite.services;

import com.darwinruiz.shoplite.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> list(int page, int size);

    int totalPages(int size);

    Optional<User> getById(int id);

    Optional<User> getByUsername(String username);

    void save(User user);      // create or update

    void delete(int id, int currentUserId); // valida no autoeliminarse

    boolean validateLogin(String username, String password);
}
