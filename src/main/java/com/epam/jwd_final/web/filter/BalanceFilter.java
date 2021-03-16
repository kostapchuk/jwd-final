package com.epam.jwd_final.web.filter;

import com.epam.jwd_final.web.command.Parameter;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.UserService;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = {"/controller"})
public class BalanceFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger(BalanceFilter.class);
    private static final String NULL = "null";

    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) {
        this.userService = UserServiceImpl.INSTANCE;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            if (httpRequest.getSession(false) != null) {
                final String userIdStr = String.valueOf(httpRequest.getSession().getAttribute(Parameter.USER_ID.getValue()));
                if (!NULL.equals(userIdStr)) {
                    final int userId = Integer.parseInt(userIdStr);
                    httpRequest.getSession().setAttribute(Parameter.USER_BALANCE.getValue(), userService.findBalanceById(userId));
                }
                chain.doFilter(request, response);
            }
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e.getCause());
        }
    }
}
