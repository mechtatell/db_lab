package ru.mechtatell.DAO.Repos;

import org.springframework.data.repository.CrudRepository;
import ru.mechtatell.Models.Employee;

public interface EmployeeRepos extends CrudRepository<Employee, Integer> {
}
