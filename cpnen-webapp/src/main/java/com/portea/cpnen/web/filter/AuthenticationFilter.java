package com.portea.cpnen.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);

    public void init(FilterConfig fConfig) throws ServletException {
        LOG.debug("AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        LOG.debug("Requested Resource::"+uri);

        HttpSession session = req.getSession(false);

        boolean urlEndsWithAllowedResource = (uri.endsWith("html") || uri.endsWith("js") || uri.endsWith("png") ||
                uri.endsWith("css") || uri.endsWith("login") || uri.endsWith("unauthorized.jsp"));


        if(session == null && urlEndsWithAllowedResource == false){
            LOG.debug("Unauthorized access request");
            res.sendRedirect("/cpnen/web/login.html");
        }else{
            // pass the request along the filter chain
               chain.doFilter(request, response);
        }

//        .matches(".*(css|jpg|png|gif|js).*")
    }



    public void destroy() {
        //close any resources here
    }
}
