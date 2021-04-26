package ru.mechtatell.DAO.Repos;

import org.springframework.data.repository.CrudRepository;
import ru.mechtatell.Models.Plan;

public interface PlanRepos extends CrudRepository<Plan, Integer> {
}
