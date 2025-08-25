package com.darwinruiz.shoplite.repositories;

import com.darwinruiz.shoplite.models.Product;

import java.util.List;
import java.util.Optional;

public interface IProductRepository {
    List<Product> findAll(int offset, int limit);

    int countAll();

    Optional<Product> findById(int id);

    void create(Product p);

    void update(Product p);

    void deleteById(int id);
}