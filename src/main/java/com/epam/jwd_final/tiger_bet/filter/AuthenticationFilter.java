package com.epam.jwd_final.tiger_bet.filter;

import com.epam.jwd_final.tiger_bet.domain.Role;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {})
public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        final String requestURI = httpServletRequest.getRequestURI();
        HttpSession session = httpServletRequest.getSession(false);
        // check existence of user
        RequestDispatcher requestDispatcher;
        if (session != null) {
            final String roleName = String.valueOf(session.getAttribute("roleName"));
            if (Role.ADMIN.name().equals(roleName.toUpperCase())) {
                requestDispatcher = servletRequest.getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
            } else {
                requestDispatcher = servletRequest.getRequestDispatcher("/WEB-INF/jsp/error.jsp");
            }
        } else {
            requestDispatcher = servletRequest.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
        }
        requestDispatcher.forward(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
