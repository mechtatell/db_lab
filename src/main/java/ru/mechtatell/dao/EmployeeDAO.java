package ru.mechtatell.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
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
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Employee> list = jdbcTemplate.query("SELECT * FROM employee", context.getBean(EmployeeMapper.class));
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeSeconds());
        return list;
    }

    public Employee show(int id) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Employee employee = jdbcTemplate.query("SELECT * FROM employee WHERE id = ?", context.getBean(EmployeeMapper.class), id)
                .stream().findAny().orElse(null);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeSeconds());
        return employee;
    }

    public List<Employee> indexOnTeam(int teamID) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Employee> list = jdbcTemplate.query("SELECT e.* FROM employee e JOIN employee_team et ON e.id = et.employee_id WHERE et.team_id = ?",
                context.getBean(EmployeeMapper.class), teamID);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeSeconds());
        return list;
    }

    public void save(Employee employee) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        jdbcTemplate.update("INSERT INTO employee (first_name, last_name, position_id) VALUES (?, ?, ?)",
                employee.getFirstName(), employee.getLastName(), employee.getPosition().getId());
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeSeconds());
    }

    public void update(int id, Employee updatedEmployee) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        jdbcTemplate.update("UPDATE Employee SET first_name=?, last_name=?, position_id=? WHERE id=?",
                updatedEmployee.getFirstName(), updatedEmployee.getLastName(), updatedEmployee.getPosition().getId(), id);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeSeconds());
    }

    public void remove(int id) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        jdbcTemplate.update("DELETE FROM employee WHERE id=?", id);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeSeconds());
    }
}
