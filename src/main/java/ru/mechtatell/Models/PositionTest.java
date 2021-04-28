package ru.mechtatell.Models;

import org.springframework.data.redis.core.RedisHash;

@RedisHash("position")
public class PositionTest {
    private int id;
    private String name;
    private double payment;

    public PositionTest(int id, String name, double payment) {
        this.id = id;
        this.name = name;
        this.payment = payment;
    }
}
