package ru.mechtatell.DAO.Repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.mechtatell.DAO.DTO.EmployeeDTO;
import ru.mechtatell.Models.Employee;

import java.util.List;

public interface EmployeeRepos extends CrudRepository<Employee, Integer> {

    @Query(value = "SELECT e.first_name AS name, e.last_name AS surname, p.name AS position, p.payment, COUNT(*) AS teams\n" +
            "FROM employee e\n" +
            "         JOIN position p on e.position_id = p.id\n" +
            "         JOIN employee_team et on e.id = et.employee_id\n" +
            "         JOIN team t on et.team_id = t.id\n" +
            "GROUP BY e.first_name, e.last_name, p.name, p.payment\n", nativeQuery = true)
    List<EmployeeDTO> findAllEmployees();
}
