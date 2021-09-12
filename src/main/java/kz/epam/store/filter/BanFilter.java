package kz.epam.store.filter;

import kz.epam.store.config.ParameterConstant;
import kz.epam.store.config.UrlMapping;
import kz.epam.store.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class BanFilter implements Filter {

    private Set<String> grantedUrls;

    @Override
    public void init(FilterConfig filterConfig) {
        grantedUrls = new HashSet<>();
        grantedUrls.add(UrlMapping.INDEX_URL);
        grantedUrls.add(UrlMapping.LOGOUT_URL);
        grantedUrls.add(UrlMapping.PROFILE_URL);
        grantedUrls.add(UrlMapping.CHANGE_LOCALE_URL);
        grantedUrls.add(UrlMapping.BANNED_URL);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession currentSession = request.getSession(false);
        User user = null;
        if(currentSession != null)
            user = (User) currentSession.getAttribute(ParameterConstant.USER);
        String bannedURL = UrlMapping.BANNED_URL;
        String requestedURL = request.getRequestURI() + "?" + request.getQueryString();
        boolean banPageRequested = bannedURL.equalsIgnoreCase(requestedURL);

        if (user != null && user.isBanned() && !grantedUrls
                .contains(request.getServletPath()) && !banPageRequested) {
            response.sendRedirect(bannedURL);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
