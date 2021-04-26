package ru.mechtatell.DAO.Repos;

import org.springframework.data.repository.CrudRepository;
import ru.mechtatell.Models.Position;

public interface PositionRepos extends CrudRepository<Position, Integer> {
}
