package ru.mechtatell.DAO.JpaReposImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.mechtatell.DAO.Interfaces.PlanDAO;
import ru.mechtatell.DAO.Repos.MaterialPlanRepos;
import ru.mechtatell.DAO.Repos.PlanRepos;
import ru.mechtatell.Models.MaterialPlan;
import ru.mechtatell.Models.Plan;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PlanDAOImpl implements PlanDAO {

    private final PlanRepos planRepos;
    private final MaterialPlanRepos materialPlanRepos;

    @Autowired
    public PlanDAOImpl(PlanRepos planRepos, MaterialPlanRepos materialPlanRepos) {
        this.planRepos = planRepos;
        this.materialPlanRepos = materialPlanRepos;
    }

    @Override
    public int save(Plan item) {
        if (item.getMaterialPlanList() != null) {
            List<MaterialPlan> materialPlanList = (List<MaterialPlan>) materialPlanRepos.findAll();
            materialPlanList.stream()
                    .filter(e -> !item.getMaterialPlanList().contains(e) && e.getPlan().getId() == item.getId())
                    .forEach(materialPlanRepos::delete);

            item.getMaterialPlanList().forEach(materialPlanRepos::save);
        }
        return planRepos.save(item).getId();
    }

    @Override
    public List<Plan> findAll() {
        return (List<Plan>) planRepos.findAll();
    }

    @Override
    public Optional<Plan> findById(int id) {
        return planRepos.findById(id);
    }

    @Override
    public void remove(int id) {
        planRepos.deleteById(id);
    }
}
