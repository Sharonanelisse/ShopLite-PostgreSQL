package com.darwinruiz.shoplite.services;

import com.darwinruiz.shoplite.models.User;
import com.darwinruiz.shoplite.repositories.IUserRepository;
import com.darwinruiz.shoplite.repositories.UserRepository;
import com.darwinruiz.shoplite.services.IUserService;

import java.util.List;
import java.util.Optional;

public class UserService implements IUserService {
    private final IUserRepository repo = new UserRepository();

    @Override
    public List<User> list(int page, int size) {
        int offset = (Math.max(page, 1) - 1) * size;
        return repo.findAll(offset, size);
    }

    @Override
    public int totalPages(int size) {
        int total = repo.countAll();
        return (int) Math.ceil(total / (double) size);
    }

    @Override
    public Optional<User> getById(int id) {
        return repo.findById(id);
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public void save(User user) {
        if (user.getId() == null) {
            if (repo.existsByUsername(user.getUsername()))
                throw new RuntimeException("El usuario ya existe");
            repo.create(user);
        } else {
            repo.update(user);
        }
    }

    @Override
    public void delete(int id, int currentUserId) {
        if (id == currentUserId)
            throw new RuntimeException("No puede eliminar su propio usuario en sesión.");
        repo.deleteById(id);
    }

    @Override
    public boolean validateLogin(String username, String password) {
        return repo.findByUsername(username)
                .filter(User::isActive)
                .filter(u -> u.getPassword().equals(password))
                .isPresent();
    }
}
