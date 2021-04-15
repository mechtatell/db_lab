package ru.mechtatell.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import ru.mechtatell.DAO.mappers.PlanMapper;
import ru.mechtatell.Models.Material;
import ru.mechtatell.Models.Plan;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class PlanDAO {

    private final ApplicationContext context;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PlanDAO(ApplicationContext context, JdbcTemplate jdbcTemplate) {
        this.context = context;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Plan> index() {
        return jdbcTemplate.query("SELECT * FROM plan", context.getBean(PlanMapper.class));
    }

    public Plan show(int id) {
        return jdbcTemplate.query("SELECT * FROM plan WHERE id=?", context.getBean(PlanMapper.class), id)
                .stream().findAny().orElse(null);
    }

    public void save(Plan plan) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement("INSERT INTO plan (construction_type, floors_count) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, plan.getConstructionType());
            statement.setInt(2, plan.getFloorsCount());
            return statement;
        }, holder);

        int id = (int) Objects.requireNonNull(holder.getKeys()).get("id");
        jdbcTemplate.update("DELETE FROM material_plan WHERE plan_id = ?", id);
        for (Map.Entry<Material, Integer> material : plan.getMaterialList().entrySet()) {
            jdbcTemplate.update("INSERT INTO material_plan VALUES (?, ?, ?)", material.getKey().getId(), id, material.getValue());
        }
    }

    public void update(int id, Plan updatedPlan) {
        jdbcTemplate.update("UPDATE plan SET construction_type=?, floors_count=? WHERE id=?", updatedPlan.getConstructionType(),
                updatedPlan.getFloorsCount(), id);
        jdbcTemplate.update("DELETE FROM material_plan WHERE plan_id=?", id);
        for (Map.Entry<Material, Integer> material : updatedPlan.getMaterialList().entrySet()) {
            jdbcTemplate.update("INSERT INTO material_plan VALUES (?, ?, ?)", material.getKey().getId(), id, material.getValue());
        }
    }

    public void remove(int id) {
        jdbcTemplate.update("DELETE FROM plan WHERE id=?", id);
    }
}
