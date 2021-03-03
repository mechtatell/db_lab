package ru.mechtatell.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mechtatell.DBConnection;
import ru.mechtatell.models.Employee;
import ru.mechtatell.models.Position;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component("EmployeeDAO")
public class EmployeeDAO implements DAO<Employee> {

    @Autowired
    private DBConnection connection;

    @Autowired
    private DAO<Position> positionDAO;

    @Autowired
    private TeamDAO teamDAO;

    public List<Employee> index() {
        List<Employee> employees = new ArrayList<>();

        try {
            Statement statement = connection.getConnection().createStatement();
            String SQL = "SELECT * FROM employee";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getInt("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setPosition(positionDAO.show(resultSet.getInt("position_id")));
                employee.setTeamList(teamDAO.indexOnEmployee(employee.getId()));
                employees.add(employee);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return employees;
    }

    public void save(Employee employee) {
        try {
            PreparedStatement statement =
                    connection.getConnection().prepareStatement("INSERT INTO employee (first_name, last_name, position_id) VALUES (?, ?, ?)");

            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setInt(3, employee.getPosition().getId());

            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Employee show(int id) {
        Employee employee = null;
        try {
            PreparedStatement statement =
                    connection.getConnection().prepareStatement("SELECT * FROM employee WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            employee = new Employee();
            employee.setId(resultSet.getInt("id"));
            employee.setFirstName(resultSet.getString("first_name"));
            employee.setLastName(resultSet.getString("last_name"));
            employee.setPosition(positionDAO.show(resultSet.getInt("position_id")));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return employee;
    }

    public void update(int id, Employee updatedEmployee) {
        try {
            PreparedStatement statement =
                    connection.getConnection().prepareStatement("UPDATE Employee SET first_name=?, last_name=?, position_id=? WHERE id=?");
            statement.setString(1, updatedEmployee.getFirstName());
            statement.setString(2, updatedEmployee.getLastName());
            statement.setInt(3, updatedEmployee.getPosition().getId());
            statement.setInt(4, id);

            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void remove(int id) {
        try {
            PreparedStatement statement =
                    connection.getConnection().prepareStatement("DELETE FROM employee WHERE id=?");
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Employee> indexOnTeam(int teamID) {
        List<Employee> employees = new ArrayList<>();

        try {
            PreparedStatement statement = connection.getConnection().prepareStatement("SELECT e.* FROM employee e JOIN employee_team et on e.id = et.employee_id WHERE et.team_id = ?");
            statement.setInt(1, teamID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getInt("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setPosition(positionDAO.show(resultSet.getInt("position_id")));
                employee.setTeamList(teamDAO.indexOnEmployee(employee.getId()));
                employees.add(employee);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return employees;
    }
}
