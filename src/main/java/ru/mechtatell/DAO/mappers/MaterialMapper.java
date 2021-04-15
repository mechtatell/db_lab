package ru.mechtatell.DAO.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.mechtatell.Models.Material;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MaterialMapper implements RowMapper<Material> {

    @Override
    public Material mapRow(ResultSet resultSet, int i) throws SQLException {
        Material material = new Material();
        material.setId(resultSet.getInt("id"));
        material.setName(resultSet.getString("name"));
        material.setPrice(resultSet.getDouble("price"));

        return material;
    }
}
