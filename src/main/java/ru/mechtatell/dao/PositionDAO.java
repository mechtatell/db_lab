package ru.mechtatell.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.mechtatell.dao.mappers.PositionMapper;
import ru.mechtatell.models.Position;

import java.util.List;

@Component("PositionDAO")
public class PositionDAO {

    private final JdbcTemplate jdbcTemplate;
    private final ApplicationContext context;

    @Autowired
    public PositionDAO(JdbcTemplate jdbcTemplate, ApplicationContext context) {
        this.jdbcTemplate = jdbcTemplate;
        this.context = context;
    }

    public List<Position> index() {
        return jdbcTemplate.query("SELECT * FROM position", context.getBean(PositionMapper.class));
    }

    public Position show(int id) {
        return jdbcTemplate.query("SELECT * FROM position WHERE id = ?", context.getBean(PositionMapper.class), id)
                .stream().findAny().orElse(null);
    }

    public void save(Position position) {
        jdbcTemplate.update("INSERT INTO position (name, payment) VALUES (?, ?)", position.getName(), position.getPayment());
    }

    public void update(int id, Position updatedPosition) {
        jdbcTemplate.update("UPDATE position SET name=?, payment=? WHERE id=?", updatedPosition.getName(), updatedPosition.getPayment(), id);
    }

    public void remove(int id) {
        jdbcTemplate.update("DELETE FROM position WHERE id=?", id);
    }
}
