package ru.mechtatell.models;

import java.util.List;

public class Team {
    private int id;
    private String name;
    private List<Employee> employeeList;
    private List<Project> projectList;

    public Team(int id, String name, List<Employee> employeeList, List<Project> projectList) {
        this.id = id;
        this.name = name;
        this.employeeList = employeeList;
        this.projectList = projectList;
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

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }
}
