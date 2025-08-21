package com.darwinruiz.shoplite.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Requisito: bloquear todo lo no público si no existe una sesión con auth=true.
 */
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest r = (HttpServletRequest) req;
        HttpServletResponse p = (HttpServletResponse) res;

        String uri = r.getRequestURI();
        /*Verificar si la URI solicitada corresponde a una página pública (index.jsp, login.jsp, /auth/login, /).*/
        boolean esPublica =
                uri.endsWith("/index.jsp") ||
                uri.endsWith("/login.jsp") ||
               uri.equals(r.getContextPath() + "/") ||
                uri.equals(r.getContextPath() + "/auth/login");
        /*Si la página es pública, permitir el acceso.*/
        if (esPublica) {
            chain.doFilter(req, res);
            return;
        }

        /*Si es privada, validar que exista una sesión con el atributo auth en true.*/
        HttpSession session = r.getSession(false);
        Object auth = (session != null) ? session.getAttribute("auth") : null;
        boolean autenticado = (auth instanceof Boolean && (Boolean) auth);

        /*Si no cumple, redirigir a login.jsp.*/
        if (autenticado) {
            chain.doFilter(req, res);
        } else {
            p.sendRedirect(r.getContextPath() + "/login.jsp");
        }
    }
}
