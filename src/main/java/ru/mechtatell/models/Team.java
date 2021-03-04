package ru.mechtatell.models;

import java.util.List;
import java.util.Objects;

public class Team {
    private int id;
    private String name;
    private List<Employee> employeeList;

    public Team(String name, List<Employee> employeeList) {
        this.name = name;
        this.employeeList = employeeList;
    }

    public Team() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return id == team.id &&
                name.equals(team.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
