package ru.mechtatell.Models;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "plan")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String constructionType;
    private int floorsCount;

    @OneToMany(mappedBy = "plan")
    private List<MaterialPlan> materialPlanList;

    @OneToMany(mappedBy = "plan")
    private List<Project> projectList;
}
