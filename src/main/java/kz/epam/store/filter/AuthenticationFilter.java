package kz.epam.store.filter;

import kz.epam.store.config.ParameterConstant;
import kz.epam.store.config.UrlMapping;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession currentSession = request.getSession(false);

        boolean userLoggedIn = currentSession != null && currentSession.getAttribute(ParameterConstant.USER) != null;

        if(userLoggedIn)
            filterChain.doFilter(servletRequest, servletResponse);
        else
            response.sendRedirect(UrlMapping.LOGIN_URL);
    }
}
