package ru.mechtatell.DAO.Repos;

import org.springframework.data.repository.CrudRepository;
import ru.mechtatell.Models.Team;

public interface TeamRepos extends CrudRepository<Team, Integer> {
}
