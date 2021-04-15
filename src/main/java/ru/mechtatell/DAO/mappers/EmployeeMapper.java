package ru.mechtatell.DAO.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.mechtatell.DAO.PositionDAO;
import ru.mechtatell.Models.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EmployeeMapper implements RowMapper<Employee> {

    private final PositionDAO positionDAO;

    @Autowired
    public EmployeeMapper(PositionDAO positionDAO) {
        this.positionDAO = positionDAO;
    }

    @Override
    public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
        Employee employee = new Employee();
        employee.setId(resultSet.getInt("id"));
        employee.setFirstName(resultSet.getString("first_name"));
        employee.setLastName(resultSet.getString("last_name"));
        employee.setPosition(positionDAO.show(resultSet.getInt("position_id")));

        return employee;
    }
}
