package com.rewqty.fileslab.servlets;

import com.rewqty.fileslab.models.UserProfileModel;
import com.rewqty.fileslab.services.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null || action.isEmpty()) {
            action = "login";
            resp.sendRedirect(req.getContextPath() + req.getServletPath() + "?action=" + action);
        }

        AccountService accountService = AccountService.getInstance();
        UserProfileModel profile = accountService.getUserBySessionId(req.getSession().getId());
        if (profile == null) {
            if (action.equalsIgnoreCase("login")) {
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            } else if (action.equalsIgnoreCase("registration")) {
                req.getRequestDispatcher("registration.jsp").forward(req, resp);
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/files");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountService accountService = AccountService.getInstance();
        String action = req.getParameter("action");
        if (action == null) {
            action = "login";
        }

        if (action.equalsIgnoreCase("logout")) {
            accountService.deleteSession(req.getSession().getId());
            resp.sendRedirect(req.getContextPath() + "/auth");
            return;
        }

        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String pass = req.getParameter("password");

        if (action.equalsIgnoreCase("registration")) {

            if (login.equals("") || pass.equals("") || email.equals("")) {
                req.getRequestDispatcher("registration.jsp").forward(req, resp);
                return;
            }
            UserProfileModel newProfile = new UserProfileModel(login, pass, email);
            accountService.addNewUser(newProfile);
            accountService.addSession(req.getSession().getId(), newProfile);

        } else {
            UserProfileModel profile = accountService.getUserByLogin(login);
            if (profile == null || !profile.getPass().equals(pass)) {
                req.getRequestDispatcher("login.jsp").forward(req, resp);
                return;
            }
            accountService.addSession(req.getSession().getId(), profile);
        }

        resp.sendRedirect(req.getContextPath() + "/files");
    }
}
