package com.laowang.web.filter;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Administrator on 2016/12/16.
 */
public class EncodingFilter extends AbstractFilter {
    private String encoding = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String result = filterConfig.getInitParameter("encoding");
        if(StringUtils.isNotEmpty(result)){
            encoding = result;

        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(encoding);
        servletResponse.setCharacterEncoding(encoding);

        filterChain.doFilter(servletRequest,servletResponse);
    }

}
