package ru.mechtatell.DAO.JpaReposImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.mechtatell.DAO.Interfaces.EmployeeDAO;
import ru.mechtatell.DAO.Repos.EmployeeRepos;
import ru.mechtatell.DAO.DTO.EmployeeDTO;
import ru.mechtatell.Models.Employee;

import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    private final EmployeeRepos employeeRepos;

    @Autowired
    public EmployeeDAOImpl(EmployeeRepos employeeRepos) {
        this.employeeRepos = employeeRepos;
    }

    @Override
    public int save(Employee item) {
        return employeeRepos.save(item).getId();
    }

    @Override
    public List<Employee> findAll() {
        return (List<Employee>) employeeRepos.findAll();
    }

    @Override
    public Optional<Employee> findById(int id) {
        return employeeRepos.findById(id);
    }

    @Override
    public void remove(int id) {
        employeeRepos.deleteById(id);
    }

    @Override
    public List<EmployeeDTO> findEmployeeStat() {
        return employeeRepos.findAllEmployees();
    }
}
