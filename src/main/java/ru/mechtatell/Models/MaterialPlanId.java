package ru.mechtatell.Models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class MaterialPlanId implements Serializable {
    @Column(name = "material_id")
    private int materialId;

    @Column(name = "plan_id")
    private int planId;
}
