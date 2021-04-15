package ru.mechtatell.Models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToMany(mappedBy = "employeeList")
    private List<Team> teamList;
}
