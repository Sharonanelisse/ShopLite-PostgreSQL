package com.darwinruiz.shoplite.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconnection {
    private static final String URL = "jdbc:postgresql://localhost:5433/ShopLite";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin123";

    private static Connection connection;

    private dbconnection() {
    }

    public static Connection getConnection() {
        if (connection != null) return connection;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo abrir conexión: " + e.getMessage(), e);
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
