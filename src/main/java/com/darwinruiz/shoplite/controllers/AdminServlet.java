package com.darwinruiz.shoplite.controllers;

import com.darwinruiz.shoplite.models.Product;
import com.darwinruiz.shoplite.repositories.ProductRepository;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Requisito (POST): validar y crear un nuevo producto en memoria y redirigir a /home.
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            req.getRequestDispatcher("/admin.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    /*Leer name y price desde el formulario.*/

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String priceIni = req.getParameter("price");

        /*Validar que el nombre no esté vacío y el precio sea mayor a 0.*/
        if (name == null || name.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/admin?err=1");
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceIni);
            if (price <= 0) {
                resp.sendRedirect(req.getContextPath() + "/admin?err=1");
                return;
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/admin?err=1");
            return;
        }

        /*• Usar repo.nextId() para generar el nuevo ID y guardar el producto.
        • Si es válido, redirigir a /home.
        • Si no es válido, redirigir a /admin?err=1.*/

        long id = ProductRepository.nextId();
        Product p = new Product(id, name, price);
        ProductRepository.add(p);

        resp.sendRedirect(req.getContextPath() + "/home");
    }
}