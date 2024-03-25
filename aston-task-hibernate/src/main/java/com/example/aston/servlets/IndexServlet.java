package com.example.aston.servlets;

import com.example.aston.dao.CountryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("")
public class IndexServlet extends HttpServlet {
    private final CountryDAO countryDAO = new CountryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("countries", countryDAO.getAllCountries());
        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
