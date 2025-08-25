package com.darwinruiz.shoplite.repositories;

import com.darwinruiz.shoplite.models.Product;
import com.darwinruiz.shoplite.database.dbconnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class ProductRepository implements IProductRepository {

    @Override
    public List<Product> findAll(int offset, int limit) {
        String sql = "SELECT id, name, price, stock FROM products ORDER BY id LIMIT ? OFFSET ?";
        try (PreparedStatement ps = dbconnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            List<Product> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countAll() {
        try (Statement st = dbconnection.getConnection().createStatement()) {
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM products");
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Product> findById(int id) {
        String sql = "SELECT id, name, price, stock FROM products WHERE id=?";
        try (PreparedStatement ps = dbconnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? Optional.of(map(rs)) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(Product p) {
        String sql = "INSERT INTO products (name, price, stock) VALUES (?, ?, ?)";
        try (PreparedStatement ps = dbconnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setBigDecimal(2, p.getPrice());
            ps.setInt(3, p.getStock());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Product p) {
        String sql = "UPDATE products SET name=?, price=?, stock=? WHERE id=?";
        try (PreparedStatement ps = dbconnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setBigDecimal(2, p.getPrice());
            ps.setInt(3, p.getStock());
            ps.setInt(4, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM products WHERE id=?";
        try (PreparedStatement ps = dbconnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Product map(ResultSet rs) throws SQLException {
        return new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getBigDecimal("price"),
                rs.getInt("stock")
        );
    }
}