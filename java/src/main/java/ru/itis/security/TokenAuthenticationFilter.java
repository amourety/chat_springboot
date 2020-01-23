package ru.itis.security;


import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class TokenAuthenticationFilter implements Filter {

    private final static String AUTH_HEADER = "AUTH";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String tokenValue = request.getHeader(AUTH_HEADER);
        if (tokenValue != null) {

            TokenAuthentication authentication = new TokenAuthentication();
            authentication.setToken(tokenValue);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}