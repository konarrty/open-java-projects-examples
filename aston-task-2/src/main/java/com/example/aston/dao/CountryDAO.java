package com.example.aston.dao;

import com.example.aston.conn.ConnectionHolder;
import com.example.aston.mapper.CountryMapper;
import com.example.aston.model.Country;

import java.sql.*;
import java.util.Collection;

public class CountryDAO {

    private final CountryMapper countryMapper = new CountryMapper();

    public Collection<Country> getAllCountries() {
        try (Connection connection = ConnectionHolder.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select * from countries");

            ResultSet resultSet = statement.executeQuery();
            return countryMapper.mapToList(resultSet);
        } catch (SQLException e) {
            return null;
        }
    }

    public Country getCountryByName(String name) {
        try (Connection connection = ConnectionHolder.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select * from countries where name = ?");
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();
            return countryMapper.mapToObject(resultSet);
        } catch (SQLException e) {
            return null;
        }
    }
}
