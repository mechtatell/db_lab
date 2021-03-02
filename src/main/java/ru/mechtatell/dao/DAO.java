package ru.mechtatell.dao;

import java.util.List;

public interface DAO<T> {
    List<T> index();
    T show(int id);
    void save(T item);
    void update(int id, T updatedItem);
    void remove(int id);
}
