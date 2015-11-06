package com.portea.cpnen.web.servlet;

import com.portea.cpnen.dao.UserDao;
import com.portea.dao.JpaDao;
import com.portea.util.BCrypt;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/web/login")
public class Login extends HttpServlet {

    @Inject
    @JpaDao
    UserDao userDao;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        response.sendRedirect("index.html");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean isValidUser;

        try{
            String storedPassword = userDao.getPassword(username);
            isValidUser = BCrypt.checkpw(password, storedPassword);
        }catch (NoResultException e){
            isValidUser = false;
        }

        if(!isValidUser){
            System.out.println("commited: " + response.isCommitted());
            request.setAttribute("ErrorMessage", "Not a valid login. Please try again");

            RequestDispatcher rd = request.getRequestDispatcher("unauthorized.jsp");
            rd.forward(request, response);
            return;
        }

        Integer userId = userDao.getUserId(username);

        HttpSession session = request.getSession();

        session.setAttribute("userId", userId);

        session.setAttribute("user", username);

        response.sendRedirect("/cpnen/web/main.jsp");

    }

}
