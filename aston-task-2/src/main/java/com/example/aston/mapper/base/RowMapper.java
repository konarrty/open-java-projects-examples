package com.example.aston.mapper.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface RowMapper<T> {

    T mapToObject(ResultSet rs) throws SQLException;

    List<T> mapToList(ResultSet rs) throws SQLException;


}
