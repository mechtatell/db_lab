package ru.mechtatell.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.mechtatell.dao.mappers.MaterialMapper;
import ru.mechtatell.dao.mappers.MaterialMapperMap;
import ru.mechtatell.models.CountOfMaterial;
import ru.mechtatell.models.Material;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MaterialDAO {

    private final ApplicationContext context;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MaterialDAO(ApplicationContext context, JdbcTemplate jdbcTemplate) {
        this.context = context;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Material> index() {
        return jdbcTemplate.query("SELECT * FROM material", context.getBean(MaterialMapper.class));
    }

    public Map<Material, Integer> indexMap(int id) {
        return jdbcTemplate.query("SELECT * FROM material_plan WHERE plan_id=?", context.getBean(MaterialMapperMap.class), id)
                .stream().collect(Collectors.toMap(CountOfMaterial::getMaterial, CountOfMaterial::getCount));
    }

    public Material show(int id) {
        return jdbcTemplate.query("SELECT * FROM material WHERE id=?", context.getBean(MaterialMapper.class), id)
                .stream().findAny().orElse(null);
    }

    public Map<Material, Integer> indexOnPlan(int planId) {
        return jdbcTemplate.query("SELECT * FROM material_plan WHERE plan_id=?", context.getBean(MaterialMapperMap.class),
                planId).stream().collect(Collectors.toMap(CountOfMaterial::getMaterial, CountOfMaterial::getCount));
    }

    public void save(Material material) {
        jdbcTemplate.update("INSERT INTO material (name, price) VALUES (?, ?)", material.getName(), material.getPrice());
    }

    public void update(int id, Material updatedMaterial) {
        jdbcTemplate.update("UPDATE material SET name=?, price=? WHERE id=?", updatedMaterial.getName(),
                updatedMaterial.getPrice(), id);
    }

    public void remove(int id) {
        jdbcTemplate.update("DELETE FROM material WHERE id=?", id);
    }
}
