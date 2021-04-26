package ru.mechtatell.Models;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "plan")
@NoArgsConstructor
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String constructionType;
    private int floorsCount;

    @OneToMany(mappedBy = "plan", fetch = FetchType.EAGER)
    private List<MaterialPlan> materialPlanList;

    @OneToMany(mappedBy = "plan")
    private List<Project> projectList;

    public Plan(String constructionType, int floorsCount) {
        this.constructionType = constructionType;
        this.floorsCount = floorsCount;
    }

    @Override
    public String toString() {
        return constructionType;
    }
}
