package ru.mechtatell.dao.mappers;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LineMapperFirst implements RowMapper<String> {

    @Override
    public String mapRow(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getString("name") + "\t | " +
                resultSet.getDate("start_date") + "\t | " +
                resultSet.getDate("end_date") + "\t | " +
                resultSet.getString("construction_type") + "\t | " +
                resultSet.getInt("floors_count") + "\t | " +
                resultSet.getDouble("price");
    }
}
