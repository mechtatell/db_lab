package ru.mechtatell.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "material_plan")
@NoArgsConstructor
public class MaterialPlan {
    @EmbeddedId
    private MaterialPlanId id;

    @ManyToOne
    @MapsId("materialId")
    private Material material;

    @ManyToOne
    @MapsId("planId")
    private Plan plan;

    private int count;

    public MaterialPlan(Material material, Plan plan, int count) {
        this.material = material;
        this.plan = plan;
        this.count = count;
        this.id = new MaterialPlanId(material.getId(), plan.getId());
    }
}
