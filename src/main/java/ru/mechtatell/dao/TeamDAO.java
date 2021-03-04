package ru.mechtatell.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import ru.mechtatell.dao.mappers.TeamMapper;
import ru.mechtatell.models.Employee;
import ru.mechtatell.models.Team;

import java.sql.*;
import java.util.List;
import java.util.Objects;

@Component
public class TeamDAO {

    private final ApplicationContext context;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TeamDAO(ApplicationContext context, JdbcTemplate jdbcTemplate) {
        this.context = context;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Team> index() {
        return jdbcTemplate.query("SELECT * FROM team", context.getBean(TeamMapper.class));
    }

    public Team show(int id) {
        return jdbcTemplate.query("SELECT * FROM team WHERE id = ?", context.getBean(TeamMapper.class), id)
                .stream().findAny().orElse(null);
    }

    public List<Team> indexOnProject(int projectID) {
        return jdbcTemplate.query("SELECT t.* FROM team t JOIN team_project tp ON t.id = tp.team_id WHERE tp.project_id = ?",
                context.getBean(TeamMapper.class), projectID);
    }

    public void save(Team team) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement("INSERT INTO team (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, team.getName());
            return statement; }, holder);

        int id = (int) Objects.requireNonNull(holder.getKeys()).get("id");
        jdbcTemplate.update("DELETE FROM employee_team WHERE team_id = ?", id);
        for (Employee employee : team.getEmployeeList()) {
            jdbcTemplate.update("INSERT INTO employee_team VALUES (?, ?)", employee.getId(), id);
        }
    }

    public void update(int id, Team updatedTeam) {
        jdbcTemplate.update("UPDATE team SET name=? WHERE id=?",
                updatedTeam.getName(), id);
        jdbcTemplate.update("DELETE FROM employee_team WHERE team_id = ?", id);
        for (Employee employee : updatedTeam.getEmployeeList()) {
            jdbcTemplate.update("INSERT INTO employee_team VALUES (?, ?)", employee.getId(), id);
        }
    }

    public void remove(int id) {
        jdbcTemplate.update("DELETE FROM team WHERE id=?", id);
    }
}
