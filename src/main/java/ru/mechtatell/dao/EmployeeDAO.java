package ru.mechtatell.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.mechtatell.dao.mappers.EmployeeMapper;
import ru.mechtatell.models.Employee;

import java.util.List;

@Component("EmployeeDAO")
public class EmployeeDAO {

    private final ApplicationContext context;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeDAO(ApplicationContext context, JdbcTemplate jdbcTemplate) {
        this.context = context;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Employee> index() {
        return jdbcTemplate.query("SELECT * FROM employee", context.getBean(EmployeeMapper.class));
    }

    public Employee show(int id) {
        return jdbcTemplate.query("SELECT * FROM employee WHERE id = ?", context.getBean(EmployeeMapper.class), id)
                .stream().findAny().orElse(null);
    }

    public List<Employee> indexOnTeam(int teamID) {
        return jdbcTemplate.query("SELECT e.* FROM employee e JOIN employee_team et ON e.id = et.employee_id WHERE et.team_id = ?",
                context.getBean(EmployeeMapper.class), teamID);
    }

    public void save(Employee employee) {
        jdbcTemplate.update("INSERT INTO employee (first_name, last_name, position_id) VALUES (?, ?, ?)",
                employee.getFirstName(), employee.getLastName(), employee.getPosition().getId());
    }

    public void update(int id, Employee updatedEmployee) {
        jdbcTemplate.update("UPDATE Employee SET first_name=?, last_name=?, position_id=? WHERE id=?",
                updatedEmployee.getFirstName(), updatedEmployee.getLastName(), updatedEmployee.getPosition().getId(), id);
    }

    public void remove(int id) {
        jdbcTemplate.update("DELETE FROM employee WHERE id=?", id);
    }
}
