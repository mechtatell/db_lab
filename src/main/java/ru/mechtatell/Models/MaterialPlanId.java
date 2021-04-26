package ru.mechtatell.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialPlanId implements Serializable {
    @Column(name = "material_id")
    private int materialId;

    @Column(name = "plan_id")
    private int planId;
}
