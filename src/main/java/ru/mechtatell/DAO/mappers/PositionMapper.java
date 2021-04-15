package ru.mechtatell.DAO.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.mechtatell.Models.Position;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PositionMapper implements RowMapper<Position> {

    @Override
    public Position mapRow(ResultSet resultSet, int i) throws SQLException {
        Position position = new Position();
        position.setId(resultSet.getInt("id"));
        position.setName(resultSet.getString("name"));
        position.setPayment(Double.parseDouble(resultSet.getString("payment")));

        return position;
    }
}

