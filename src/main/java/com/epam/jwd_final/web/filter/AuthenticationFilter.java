package com.epam.jwd_final.web.filter;

import com.epam.jwd_final.web.domain.Role;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/controller"})
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
        final String command = servletRequest.getParameter("command");
        final String roleName = String.valueOf(session.getAttribute("userRole"));
        if (command.equals("show_all_users_page")) {
            if (!Role.ADMIN.name().equals(roleName)) {
                servletRequest.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(servletRequest, servletResponse);
            }
        }
        if (command.equals("show_bookmaker_page")) {
            if (!(Role.ADMIN.name().equals(roleName) || Role.BOOKMAKER.name().equals(roleName))) {
                servletRequest.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(servletRequest, servletResponse);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
