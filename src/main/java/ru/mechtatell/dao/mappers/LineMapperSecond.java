package ru.mechtatell.dao.mappers;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LineMapperSecond implements RowMapper<String> {

    @Override
    public String mapRow(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getString("first_name") + "\t | " +
                resultSet.getString("last_name") + "\t | " +
                resultSet.getString("name") + "\t | " +
                resultSet.getDouble("payment") + "\t | " +
                resultSet.getInt("team_count");
    }
}
