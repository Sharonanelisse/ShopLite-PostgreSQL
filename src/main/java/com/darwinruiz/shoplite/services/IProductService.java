package com.darwinruiz.shoplite.services;


import com.darwinruiz.shoplite.models.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    List<Product> list(int page, int size);

    int totalPages(int size);

    Optional<Product> getById(int id);

    void save(Product p);  // create or update

    void delete(int id);
}