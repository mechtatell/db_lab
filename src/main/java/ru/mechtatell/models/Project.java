package ru.mechtatell.models;

import java.util.Date;
import java.util.List;

public class Project {
    private int id;
    private Plan plan;
    private String name;
    private Date startDate;
    private Date endDate;
    private List<Team> teamList;

    public Project(Plan plan, String name, Date startDate, Date endDate, List<Team> teamList) {
        this.plan = plan;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teamList = teamList;
    }

    public Project() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }
}
