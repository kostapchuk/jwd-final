package com.epam.jwd_final.tiger_bet.controller;

import com.epam.jwd_final.tiger_bet.dao.UserDAO;
import com.epam.jwd_final.tiger_bet.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/user")
public class Controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User userByID = new UserDAO().getUserByID(2);
            resp.getWriter().println(userByID.toString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
