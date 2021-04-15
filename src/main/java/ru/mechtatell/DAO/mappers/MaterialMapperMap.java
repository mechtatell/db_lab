package ru.mechtatell.DAO.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.mechtatell.DAO.MaterialDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MaterialMapperMap implements RowMapper<CountOfMaterial> {

    private final MaterialDAO materialDAO;

    @Autowired
    public MaterialMapperMap(MaterialDAO materialDAO) {
        this.materialDAO = materialDAO;
    }

    @Override
    public CountOfMaterial mapRow(ResultSet resultSet, int i) throws SQLException {
        CountOfMaterial countOfMaterial = new CountOfMaterial();
        countOfMaterial.setMaterial(materialDAO.show(resultSet.getInt("material_id")));
        countOfMaterial.setCount(resultSet.getInt("count"));

        return countOfMaterial;
    }
}
