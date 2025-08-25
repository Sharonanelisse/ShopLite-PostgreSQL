package com.darwinruiz.shoplite.controllers;

import com.darwinruiz.shoplite.models.Product;
import com.darwinruiz.shoplite.services.IProductService;
import com.darwinruiz.shoplite.services.ProductService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "AdminServlet",
        urlPatterns = {"/app", "/app/new", "/app/edit", "/app/save", "/app/delete"})
public class AdminServlet extends HttpServlet {
    private final IProductService service = new ProductService();
    private static final int PAGE_SIZE = 5;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        if ("/app".equals(path)) {
            int page = parse(req.getParameter("page"), 1);
            List<Product> list = service.list(page, PAGE_SIZE);
            req.setAttribute("products", list);
            req.setAttribute("page", page);
            req.setAttribute("totalPages", service.totalPages(PAGE_SIZE));
            req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
            return;
        }

        if ("/app/new".equals(path)) {
            req.setAttribute("product", new Product());
            req.getRequestDispatcher("/views/admin.jsp").forward(req, resp);
            return;
        }

        if ("/app/edit".equals(path)) {
            int id = Integer.parseInt(req.getParameter("id"));
            Optional<Product> p = service.getById(id);
            if (p.isEmpty()) {
                resp.sendError(404);
                return;
            }
            req.setAttribute("product", p.get());
            req.getRequestDispatcher("/views/admin.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String path = req.getServletPath();

        if ("/app/save".equals(path)) {
            Integer id = req.getParameter("id") == null || req.getParameter("id").isBlank() ? null
                    : Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            BigDecimal price = new BigDecimal(req.getParameter("price"));
            Integer stock = Integer.parseInt(req.getParameter("stock"));

            Product p = new Product(id, name, price, stock);
            service.save(p);
            resp.sendRedirect(req.getContextPath() + "/app");
            return;
        }

        if ("/app/delete".equals(path)) {
            int id = Integer.parseInt(req.getParameter("id"));
            service.delete(id);
            resp.sendRedirect(req.getContextPath() + "/app");
        }
    }

    private int parse(String s, int def) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return def;
        }
    }
}