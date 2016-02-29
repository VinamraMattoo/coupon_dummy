package com.portea.commp.web.servlet;

import com.portea.commp.smsen.dao.RoleDao;
import com.portea.commp.smsen.dao.UserDao;
import com.portea.commp.smsen.dao.UserRoleMappingDao;
import com.portea.commp.smsen.domain.User;
import com.portea.commp.smsen.domain.UserRole;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/web/login")
public class Login extends HttpServlet {

    @Inject
    @JpaDao
    UserDao userDao;

    @Inject
    @JpaDao
    UserRoleMappingDao userRoleMappingDao;

    @Inject
    @JpaDao
    RoleDao roleDao;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String contextPath = request.getContextPath();
        response.sendRedirect(contextPath + "/web/login.html");
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean registeredUser = isUserRegistered(username, password);

        String errorMsg;
        if( ! registeredUser) {
            errorMsg = "Not a valid login. Please try again";
            redirectUser(errorMsg, request, response);
            return;
        }

        User user = userDao.getUser(username);

        Integer authorizedRoleId;
        try {

             authorizedRoleId = roleDao.getRoleId(UserRole.SMS_MANAGER.getName());
        }
        catch (NoResultException e) {
            errorMsg = "Unauthorized access. Please enter valid credentials";
            redirectUser(errorMsg, request, response);
            return;
        }

        boolean userHasValidRole = isUserRoleValid(user, authorizedRoleId);

        if ( ! userHasValidRole) {
            errorMsg = "Unauthorized access. Please enter valid credentials";
            redirectUser(errorMsg, request, response);
            return;
        }

        HttpSession session = request.getSession();

        session.setAttribute("userId", user.getId());

        session.setAttribute("userName", username);

        String contextPath = request.getContextPath();

        response.sendRedirect(contextPath+"/web/jsp/main.jsp");

    }

    private void redirectUser(String errorMsg, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("ErrorMessage", errorMsg);

        RequestDispatcher rd = request.getRequestDispatcher("/web/unauthorized.jsp");
        rd.forward(request, response);
    }

    private boolean isUserRegistered(String username, String password) {
        boolean isValidUser;
        try{
            String storedPassword = userDao.getPassword(username);
            isValidUser = BCrypt.checkpw(password, storedPassword);
        }catch (NoResultException e){
            isValidUser = false;
        }
        return isValidUser;
    }

    private boolean isUserRoleValid(User user, Integer authorizedRoleId) {
        List<Integer> assignedRoles = userRoleMappingDao.getUserRoleIds(user.getId());
        if (assignedRoles.size() == 0) {
            return false;
        }

        List<Integer> authorizedRoles = new ArrayList<>();
        //TODO: get all the children of authorized id as they also will be authorized to access sms management.
        authorizedRoles.add(authorizedRoleId);

        return assignedRoles.stream()
                          .anyMatch(userRoleId -> authorizedRoles.stream()
                                                             .anyMatch(authRoleId -> authRoleId.equals(userRoleId)));
    }

    private List<Integer> getAncestors(Integer roleId) { //TODO: optimize by using procedural query to get result in db call.
        List<Integer> roleIds = new ArrayList<>();
        Integer currentRoleId = roleId;
        while (true) {
            Integer parentRoleId = roleDao.getParentRoleId(currentRoleId);
            if (parentRoleId == null || parentRoleId == -1) {
                break;
            }
            roleIds.add(parentRoleId);
            currentRoleId = parentRoleId;
        }
        return roleIds;
    }

}
