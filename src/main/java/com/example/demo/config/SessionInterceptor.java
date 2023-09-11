package com.example.demo.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // Verifica si el objeto de sesión "usuario" no existe
        if (request.getSession().getAttribute("usuario") == null) {
            // Redirige al usuario a la página de inicio de sesión
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }

}
