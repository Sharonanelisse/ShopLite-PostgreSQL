package com.darwinruiz.shoplite.repositories;


import com.darwinruiz.shoplite.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    List<User> findAll(int offset, int limit);

    int countAll();

    Optional<User> findById(int id);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    void create(User user);

    void update(User user);

    void deleteById(int id);
}