package ru.mechtatell.Models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "material_plan")
public class MaterialPlan {
    @EmbeddedId
    private MaterialPlanId id;

    @ManyToOne
    @MapsId("materialId") //This is the name of attr in EmployerDeliveryAgentPK class
    @JoinColumn(name = "material_id")
    private Material material;

    @ManyToOne
    @MapsId("planId")
    @JoinColumn(name = "plan_id")
    private Plan plan;

    private int count;
}
