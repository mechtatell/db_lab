package ru.mechtatell.dao.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.mechtatell.dao.MaterialDAO;
import ru.mechtatell.models.Plan;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PlanMapper implements RowMapper<Plan> {
    private final MaterialDAO materialDAO;

    @Autowired
    public PlanMapper(MaterialDAO materialDAO) {
        this.materialDAO = materialDAO;
    }

    @Override
    public Plan mapRow(ResultSet resultSet, int i) throws SQLException {
        Plan plan = new Plan();
        plan.setId(resultSet.getInt("id"));
        plan.setConstructionType(resultSet.getString("construction_type"));
        plan.setFloorsCount(resultSet.getInt("floors_count"));
        plan.setMaterialList(materialDAO.indexMap(plan.getId()));

        return plan;
    }
}
