package org.jahia.modules.dxstatistics.filters;

import org.jahia.bin.filters.AbstractServletFilter;
import org.jahia.modules.dxstatistics.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class StatisticsFilter extends AbstractServletFilter {

    private static final Logger logger = LoggerFactory.getLogger(StatisticsFilter.class);



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String authorization = httpRequest.getHeader("Authorization");

        String uri = httpRequest.getRequestURI();

        if (uri.startsWith("/modules") || uri.startsWith("/files")) {
            Statistics.addFileCall();
        }

        if (uri.contains(".do") || uri.contains("modules/graphql")) {
            Statistics.addApiCall();
        }

        filterChain.doFilter(httpRequest, servletResponse);
    }


    @Override
    public void destroy() {

    }
}
