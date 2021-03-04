package ru.mechtatell.models;

import java.util.Objects;

public class Position {
    private int id;
    private String name;
    private double payment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Position() {
    }

    public Position(String name, double payment) {
        this.name = name;
        this.payment = payment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return id == position.id &&
                Double.compare(position.payment, payment) == 0 &&
                name.equals(position.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, payment);
    }
}
