package ru.mechtatell.Models;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface PaymentRedis extends KeyValueRepository<PositionTest, Integer> {
}
