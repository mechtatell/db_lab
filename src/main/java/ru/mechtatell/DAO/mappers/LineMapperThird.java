package ru.mechtatell.DAO.mappers;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LineMapperThird implements RowMapper<String> {
    @Override
    public String mapRow(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getString("name") + "\t | " +
                resultSet.getInt("employee_count") + "\t | " +
                resultSet.getDouble("payment_sum");
    }
}
