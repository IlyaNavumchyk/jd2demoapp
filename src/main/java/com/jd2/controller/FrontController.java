package com.jd2.controller;

import com.jd2.repository.user.UserRepository;
import com.jd2.util.DatabasePropertiesReader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doRequest(req, resp);
    }

    private void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher dispatcher = req.getRequestDispatcher("/hello");

        if (dispatcher != null) {
            System.out.println("Forward will be done!");

            int index = 0;

            req.setAttribute("user", "Slava");
            req.setAttribute("index", index);

            UserRepository userRepository = new UserRepository(new DatabasePropertiesReader());

            req.setAttribute("users", userRepository.findAll());

            dispatcher.forward(req, resp);
        }
    }
}
