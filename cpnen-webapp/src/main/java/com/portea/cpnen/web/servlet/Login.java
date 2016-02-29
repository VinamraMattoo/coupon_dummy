package com.portea.cpnen.web.servlet;

import com.portea.cpnen.dao.RoleDao;
import com.portea.cpnen.dao.UserDao;
import com.portea.cpnen.dao.UserRoleMappingDao;
import com.portea.cpnen.domain.Role;
import com.portea.cpnen.domain.User;
import com.portea.cpnen.domain.UserRole;
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

        boolean userHasValidRole = isUserRoleValid(user);

        if ( ! userHasValidRole) {
            errorMsg = "Unauthorized access. Please enter valid credentials";

            redirectUser(errorMsg, request, response);
            return;
        }

        HttpSession session = request.getSession();

        session.setAttribute("userId", user.getId());

        session.setAttribute("userName", user.getFirstName());

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

    private boolean isUserRoleValid(User user) {
        List<Integer> assignedRoles = userRoleMappingDao.getUserRoleIds(user.getId());
        if (assignedRoles.size() == 0) {
            return false;
        }

        for(Integer roleId : assignedRoles) {
            Role role = roleDao.find(roleId);
            if(role != null) {
                String roleName = roleDao.find(roleId).getName();
                if(roleName.equals(UserRole.COUPON_ADMIN.getName()) || roleName.equals(UserRole.COUPON_MANAGER_MARKETING.getName()) ||
                        roleName.equals(UserRole.COUPON_MANAGER_OPS.getName()) || roleName.equals(UserRole.COUPON_MANAGER_SALES.getName()) ||
                        roleName.equals(UserRole.COUPON_MANAGER_ENGAGEMENT.getName())) {
                    return true;
                }
            }
        }

        return false;
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
