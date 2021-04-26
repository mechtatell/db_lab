package ru.mechtatell.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "material")
@NoArgsConstructor
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;

    @OneToMany(mappedBy = "material")
    private List<MaterialPlan> materialPlanList;

    public Material(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
