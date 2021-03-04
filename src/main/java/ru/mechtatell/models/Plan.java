package ru.mechtatell.models;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Plan {
    private int id;
    private String constructionType;
    private int floorsCount;
    private Map<Material, Integer> materialList;

    public Plan() {
    }

    public Plan(String constructionType, int floorsCount, Map<Material, Integer> materialList) {
        this.constructionType = constructionType;
        this.floorsCount = floorsCount;
        this.materialList = materialList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConstructionType() {
        return constructionType;
    }

    public void setConstructionType(String constructionType) {
        this.constructionType = constructionType;
    }

    public int getFloorsCount() {
        return floorsCount;
    }

    public void setFloorsCount(int floorsCount) {
        this.floorsCount = floorsCount;
    }

    public Map<Material, Integer> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(Map<Material, Integer> materialList) {
        this.materialList = materialList;
    }

    @Override
    public String toString() {
        return constructionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plan plan = (Plan) o;
        return id == plan.id &&
                floorsCount == plan.floorsCount &&
                constructionType.equals(plan.constructionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, constructionType, floorsCount);
    }
}
