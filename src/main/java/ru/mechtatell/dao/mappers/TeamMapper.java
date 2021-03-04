package ru.mechtatell.dao.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.mechtatell.dao.EmployeeDAO;
import ru.mechtatell.models.Team;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TeamMapper implements RowMapper<Team> {

    private final EmployeeDAO employeeDAO;

    @Autowired
    public TeamMapper(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public Team mapRow(ResultSet resultSet, int i) throws SQLException {
        Team team = new Team();
        team.setId(resultSet.getInt("id"));
        team.setName(resultSet.getString("name"));
        team.setEmployeeList(employeeDAO.indexOnTeam(team.getId()));

        return team;
    }
}
