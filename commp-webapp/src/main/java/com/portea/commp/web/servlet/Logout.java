package com.portea.commp.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/web/logout")
public class Logout extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("JSESSIONID")){
                    System.out.println("JSESSIONID="+cookie.getValue());
                }
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }

        //invalidate the session if exists
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }

        //no encoding because we have invalidated the session
        String contextPath = request.getContextPath();
        response.sendRedirect(contextPath+"/web/login.html");

    }

}
