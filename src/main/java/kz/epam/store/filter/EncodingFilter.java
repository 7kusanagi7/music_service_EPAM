package kz.epam.store.filter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class EncodingFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger(EncodingFilter.class);

    private static final String ENCODING_PARAMETER = "encoding";
    private String encoding;

    @Override
    public void init(final FilterConfig filterConfig) {
        encoding = filterConfig.getInitParameter(ENCODING_PARAMETER);
    }

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain
            filterChain) throws IOException, ServletException {
        try {
            servletRequest.setCharacterEncoding(encoding);
            servletResponse.setCharacterEncoding(encoding);
        } catch (UnsupportedEncodingException e) {
            LOGGER.warn("Using default request encoding (not utf-8)", e);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        encoding = null;
    }
}
