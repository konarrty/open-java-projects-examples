package com.example.aston.servlets;

import com.example.aston.api.TemperatureApi;
import com.example.aston.api.TemperatureApiHolder;
import com.example.aston.enums.Country;
import com.example.aston.utils.DateFormatUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/weather")
public class WeatherServlet extends HttpServlet {
    private final TemperatureApi temperatureApi = TemperatureApiHolder.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String countryName = req.getParameterMap().get("country")[0];
        Country country = Country.valueOf(countryName);

        req.setAttribute("country", country);
        req.setAttribute("temperature", temperatureApi.getTemperatureByCountry(country));
        req.setAttribute("dateTime", DateFormatUtils.format(LocalDateTime.now()));

        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
