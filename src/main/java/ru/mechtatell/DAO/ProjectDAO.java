package ru.mechtatell.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import ru.mechtatell.DAO.mappers.ProjectMapper;
import ru.mechtatell.Models.Project;
import ru.mechtatell.Models.Team;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Component
public class ProjectDAO {
    private final ApplicationContext context;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProjectDAO(ApplicationContext context, JdbcTemplate jdbcTemplate) {
        this.context = context;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Project> index() {
        return jdbcTemplate.query("SELECT * FROM project", context.getBean(ProjectMapper.class));
    }

    public Project show(int id) {
        return jdbcTemplate.query("SELECT * FROM project WHERE id = ?", context.getBean(ProjectMapper.class), id)
                .stream().findAny().orElse(null);
    }

    public void save(Project project) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement("INSERT INTO project (name, plan_id, start_date) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, project.getName());
            statement.setInt(2, project.getPlan().getId());
            statement.setDate(3, (Date) project.getStartDate());
            return statement; }, holder);

        int id = (int) Objects.requireNonNull(holder.getKeys()).get("id");
        jdbcTemplate.update("DELETE FROM team_project WHERE project_id = ?", id);
        for (Team team : project.getTeamList()) {
            jdbcTemplate.update("INSERT INTO team_project VALUES (?, ?)", team.getId(), id);
        }
    }

    public void update(int id, Project updatedProject) {
        jdbcTemplate.update("UPDATE project SET name=?, plan_id=?, start_date=?, end_date=? WHERE id=?",
                updatedProject.getName(), updatedProject.getPlan().getId(), updatedProject.getStartDate(), updatedProject.getEndDate(), id);
        jdbcTemplate.update("DELETE FROM team_project WHERE project_id = ?", id);
        for (Team team : updatedProject.getTeamList()) {
            jdbcTemplate.update("INSERT INTO team_project VALUES (?, ?)", team.getId(), id);
        }
    }

    public void remove(int id) {
        jdbcTemplate.update("DELETE FROM project WHERE id=?", id);
    }
}
