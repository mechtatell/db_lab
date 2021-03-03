package ru.mechtatell.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mechtatell.DBConnection;
import ru.mechtatell.models.Employee;
import ru.mechtatell.models.Team;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class TeamDAO implements DAO<Team> {

    @Autowired
    private DBConnection connection;

    @Autowired
    private EmployeeDAO employeeDAO;

    public List<Team> index() {
        List<Team> teams = new ArrayList<>();

        try {
            Statement statement = connection.getConnection().createStatement();
            String SQL = "SELECT * FROM team";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Team team = new Team();
                team.setId(resultSet.getInt("id"));
                team.setName(resultSet.getString("first_name"));
                team.setEmployeeList(employeeDAO.indexOnTeam(team.getId()));
                //
                teams.add(team);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return teams;
    }

    public void save(Team team) {
        try {
            PreparedStatement statement =
                    connection.getConnection().prepareStatement("INSERT INTO team (name) VALUES (?)");

            statement.setString(1, team.getName());
            statement.executeUpdate();

            for (Employee employee : team.getEmployeeList()) {
                statement = connection.getConnection().prepareStatement("INSERT INTO employee_team VALUES (?, ?)");
                statement.setInt(1, employee.getId());
                statement.setInt(2, team.getId());
                statement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Team show(int id) {
        Team team = null;
        try {
            PreparedStatement statement =
                    connection.getConnection().prepareStatement("SELECT * FROM team WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            team = new Team();
            team.setId(resultSet.getInt("id"));
            team.setName(resultSet.getString("name"));
            team.setEmployeeList(employeeDAO.indexOnTeam(id));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return team;
    }

    public void update(int id, Team updatedTeam) {
        try {
            PreparedStatement statement =
                    connection.getConnection().prepareStatement("UPDATE team SET name=? WHERE id=?");
            statement.setString(1, updatedTeam.getName());
            statement.setInt(2, id);
            statement.executeUpdate();

            statement = connection.getConnection().prepareStatement("DELETE FROM employee_team WHERE team_id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();

            for (Employee employee : updatedTeam.getEmployeeList()) {
                statement = connection.getConnection().prepareStatement("INSERT INTO employee_team VALUES (?, ?)");
                statement.setInt(1, employee.getId());
                statement.setInt(2, id);
                statement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void remove(int id) {
        try {
            PreparedStatement statement =
                    connection.getConnection().prepareStatement("DELETE FROM team WHERE id=?");
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Team> indexOnEmployee(int employeeId) {
        List<Team> teams = new ArrayList<>();

        try {
            PreparedStatement statement = connection.getConnection().prepareStatement("SELECT t.* FROM team t JOIN employee_team et on t.id = et.team_id WHERE et.employee_id = ?");
            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Team team = new Team();
                team.setId(resultSet.getInt("id"));
                team.setName(resultSet.getString("name"));
                team.setEmployeeList(employeeDAO.indexOnTeam(team.getId()));
                teams.add(team);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return teams;
    }
}
