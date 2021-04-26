package ru.mechtatell.DAO.Interfaces;

import ru.mechtatell.DAO.DTO.EmployeeDTO;
import ru.mechtatell.Models.Employee;

import java.util.List;

public interface EmployeeDAO extends DAO<Employee>{
    List<EmployeeDTO> findEmployeeStat();
}
