package ru.mechtatell.Models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "position")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double payment;

    @OneToMany(mappedBy = "position")
    private List<Employee> employeeList;
}
