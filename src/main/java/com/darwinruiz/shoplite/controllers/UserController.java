package com.darwinruiz.shoplite.controllers;

import com.darwinruiz.shoplite.models.User;
import com.darwinruiz.shoplite.services.IUserService;
import com.darwinruiz.shoplite.services.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "UserController",
        urlPatterns = {"/app/users", "/app/users/new", "/app/users/edit", "/app/users/save", "/app/users/delete"})
public class UserController extends HttpServlet {
    private final IUserService service = new UserService();
    private static final int PAGE_SIZE = 5;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        if ("/app/users".equals(path)) {
            int page = parse(req.getParameter("page"), 1);
            List<User> users = service.list(page, PAGE_SIZE);
            req.setAttribute("users", users);
            req.setAttribute("page", page);
            req.setAttribute("totalPages", service.totalPages(PAGE_SIZE));
            req.getRequestDispatcher("/views/users/list.jsp").forward(req, resp);
            return;
        }

        if ("/app/users/new".equals(path)) {
            req.setAttribute("user", new User());
            req.getRequestDispatcher("/views/users/form.jsp").forward(req, resp);
            return;
        }

        if ("/app/users/edit".equals(path)) {
            int id = Integer.parseInt(req.getParameter("id"));
            Optional<User> u = service.getById(id);
            if (u.isEmpty()) {
                resp.sendError(404);
                return;
            }
            req.setAttribute("user", u.get());
            req.getRequestDispatcher("/views/users/form.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String path = req.getServletPath();

        if ("/app/users/save".equals(path)) {
            Integer id = req.getParameter("id") == null || req.getParameter("id").isBlank() ? null
                    : Integer.parseInt(req.getParameter("id"));
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String role = req.getParameter("role");
            boolean active = req.getParameter("active") != null;

            User u = new User(id, username, password, role, active);
            try {
                service.save(u);
                resp.sendRedirect(req.getContextPath() + "/app/users");
            } catch (RuntimeException ex) {
                req.setAttribute("error", ex.getMessage());
                req.setAttribute("user", u);
                req.getRequestDispatcher("/views/users/form.jsp").forward(req, resp);
            }
            return;
        }

        if ("/app/users/delete".equals(path)) {
            int id = Integer.parseInt(req.getParameter("id"));
            int currentUserId = ((User) req.getSession().getAttribute("user")).getId();
            try {
                service.delete(id, currentUserId);
                resp.sendRedirect(req.getContextPath() + "/app/users");
            } catch (RuntimeException ex) {
                req.setAttribute("error", ex.getMessage());
                doGet(req, resp); // recarga listado con error
            }
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
