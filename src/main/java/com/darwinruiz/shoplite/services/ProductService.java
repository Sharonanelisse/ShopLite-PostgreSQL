package com.darwinruiz.shoplite.services;

import com.darwinruiz.shoplite.models.Product;
import com.darwinruiz.shoplite.repositories.IProductRepository;
import com.darwinruiz.shoplite.repositories.ProductRepository;
import com.darwinruiz.shoplite.services.IProductService;

import java.util.List;
import java.util.Optional;

public class ProductService implements IProductService {
    private final IProductRepository repo = new ProductRepository();

    @Override
    public List<Product> list(int page, int size) {
        int offset = (Math.max(page, 1) - 1) * size;
        return repo.findAll(offset, size);
    }

    @Override
    public int totalPages(int size) {
        int total = repo.countAll();
        return (int) Math.ceil(total / (double) size);
    }

    @Override
    public Optional<Product> getById(int id) {
        return repo.findById(id);
    }

    @Override
    public void save(Product p) {
        if (p.getId() == null) repo.create(p);
        else repo.update(p);
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
    }
}