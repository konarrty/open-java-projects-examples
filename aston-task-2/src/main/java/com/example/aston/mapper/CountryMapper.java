package com.example.aston.mapper;


import com.example.aston.mapper.base.RowMapper;
import com.example.aston.model.Country;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryMapper implements RowMapper<Country> {
    @Override
    public Country mapToObject(ResultSet rs) {
        Country country;
        try {
            rs.next();
            country = buildCountry(rs);
        } catch (SQLException ex) {

            return null;
        }
        return country;

    }

    @Override
    public List<Country> mapToList(ResultSet rs) {
        List<Country> countries = new ArrayList<>();
        try {
            while (rs.next()) {
                countries.add(buildCountry(rs));
            }
        } catch (SQLException exception) {
            throw new RuntimeException();
        }

        return countries;
    }

    private Country buildCountry(ResultSet rs) throws SQLException {

        return Country.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .latitude(rs.getDouble("latitude"))
                .longitude(rs.getDouble("longitude"))
                .build();
    }
}
