package ru.mechtatell.models;

import java.util.Objects;

public class Material {
    private int id;
    private String name;
    private double price;

    public Material() {
    }

    public Material(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Material material = (Material) o;
        return id == material.id &&
                Double.compare(material.price, price) == 0 &&
                name.equals(material.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }
}
