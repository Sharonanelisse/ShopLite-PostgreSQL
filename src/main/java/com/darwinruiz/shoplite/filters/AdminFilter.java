package com.darwinruiz.shoplite.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Requisito: permitir /admin solo a usuarios con rol "ADMIN"; los demás ven 403.jsp.
 */
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest r = (HttpServletRequest) req;
        HttpServletResponse p = (HttpServletResponse) res;
        /*Validar que exista una sesión activa.*/
        HttpSession session = r.getSession(false);

        Object role = (session != null) ? session.getAttribute("role") : null;
        /*Revisar que el atributo role tenga el valor "ADMIN".*/
        boolean admin = "ADMIN".equals(role);

        /*• Si no cumple, hacer forward a 403.jsp.
        • Si cumple, permitir el acceso.*/
        if(admin) {
        chain.doFilter(req, res); }
        else {
            RequestDispatcher dispatcher = r.getRequestDispatcher("/403.jsp");
            dispatcher.forward(req, res);
        }
    }
}
