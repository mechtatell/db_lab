package ru.mechtatell.DAO.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.mechtatell.DAO.PlanDAO;
import ru.mechtatell.DAO.TeamDAO;
import ru.mechtatell.Models.Project;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProjectMapper implements RowMapper<Project> {

    private final TeamDAO teamDAO;
    private final PlanDAO planDAO;

    @Autowired
    public ProjectMapper(TeamDAO teamDAO, PlanDAO planDAO) {
        this.teamDAO = teamDAO;
        this.planDAO = planDAO;
    }

    @Override
    public Project mapRow(ResultSet resultSet, int i) throws SQLException {
        Project project = new Project();
        project.setId(resultSet.getInt("id"));
        project.setName(resultSet.getString("name"));
        project.setStartDate(resultSet.getDate("start_date"));
        project.setEndDate(resultSet.getDate("end_date"));
        project.setPlan(planDAO.show(resultSet.getInt("plan_id")));
        project.setTeamList(teamDAO.indexOnProject(project.getId()));

        return project;
    }
}
