package kz.epam.store.filter;

import kz.epam.store.config.ParameterConstant;
import kz.epam.store.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession currentSession = request.getSession(false);
        if (currentSession == null || currentSession.getAttribute(ParameterConstant.USER) == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            authorize(servletRequest, servletResponse, filterChain);
        }
    }

    private void authorize(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        User currentUser = (User) request.getSession().getAttribute(ParameterConstant.USER);
        boolean isAdmin = currentUser.isAdmin();

        if(isAdmin){
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
