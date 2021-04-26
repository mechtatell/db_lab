package ru.mechtatell.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "position")
@NoArgsConstructor
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double payment;

    @OneToMany(mappedBy = "position", fetch = FetchType.EAGER)
    private List<Employee> employeeList;

    public Position(String name, double payment) {
        this.name = name;
        this.payment = payment;
    }

    @Override
    public String toString() {
        return name;
    }
}
