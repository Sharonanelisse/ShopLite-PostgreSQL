package com.darwinruiz.shoplite.controllers;

import com.darwinruiz.shoplite.models.User;
import com.darwinruiz.shoplite.services.IUserService;
import com.darwinruiz.shoplite.services.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Optional;


@WebServlet(name = "LoginServlet", urlPatterns = {"/login", "/logout"})
public class LoginServlet extends HttpServlet {
    private final IUserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("/logout".equals(req.getServletPath())) {
            HttpSession s = req.getSession(false);
            if (s != null) s.invalidate();
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (userService.validateLogin(username, password)) {
            Optional<User> u = userService.getByUsername(username);
            HttpSession s = req.getSession(true);
            s.setAttribute("user", u.get());
            resp.sendRedirect(req.getContextPath() + "/app/products");

        } else {
            req.setAttribute("error", "Credenciales inválidas o usuario inactivo.");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }
}
