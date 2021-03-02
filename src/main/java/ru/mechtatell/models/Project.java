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
}
