package ru.mechtatell.DAO.Repos;

import org.springframework.data.repository.CrudRepository;
import ru.mechtatell.Models.Project;

public interface ProjectRepos extends CrudRepository<Project, Integer> {
}
