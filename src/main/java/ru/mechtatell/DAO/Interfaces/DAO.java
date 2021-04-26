package ru.mechtatell.DAO.Interfaces;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    int save(T item);
    List<T> findAll();
    Optional<T> findById(int id);
    void remove(int id);
}
