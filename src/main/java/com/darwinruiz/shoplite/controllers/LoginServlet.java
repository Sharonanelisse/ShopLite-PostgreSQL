package com.darwinruiz.shoplite.controllers;

import com.darwinruiz.shoplite.models.User;
import com.darwinruiz.shoplite.repositories.UserRepository;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

/**
 * Requisito: autenticar, regenerar sesión y guardar auth, userEmail, role, TTL.
 */
@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
    private final UserRepository users = new UserRepository();
    /*Verificar las credenciales usando UserRepository.*/

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        String pass = req.getParameter("password");

        Optional<User> u = users.findByEmail(email);

        /*Si son inválidas, redirigir a login.jsp?err=1.*/
        if (u.isEmpty() || !u.get().getPassword().equals(pass)) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?err=1");
            return;
        }

        HttpSession oldSession = req.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }

        /*Leer email y password del formulario.*/
        HttpSession newSession = req.getSession(true);
        newSession.setAttribute("auth", true);
        newSession.setAttribute("userEmail", u.get().getEmail());
        newSession.setAttribute("role", u.get().getRole());

        /*Configurar maxInactiveInterval a 30 minutos.*/
        newSession.setMaxInactiveInterval(30 * 60);

        /*Si son válidas:*/
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}
