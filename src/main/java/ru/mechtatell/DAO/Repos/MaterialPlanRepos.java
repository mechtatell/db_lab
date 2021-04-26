package ru.mechtatell.DAO.Repos;

import org.springframework.data.repository.CrudRepository;
import ru.mechtatell.Models.MaterialPlan;
import ru.mechtatell.Models.MaterialPlanId;

public interface MaterialPlanRepos extends CrudRepository<MaterialPlan, MaterialPlanId> {
}
