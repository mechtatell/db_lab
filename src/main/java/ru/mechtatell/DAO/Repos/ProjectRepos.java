package ru.mechtatell.DAO.Repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.mechtatell.DAO.DTO.ProjectDTO;
import ru.mechtatell.Models.Project;

import java.util.List;

public interface ProjectRepos extends CrudRepository<Project, Integer> {

    @Query(value = "SELECT pr.name,\n" +
            "       pr.start_date AS start,\n" +
            "       pr.end_date AS end,\n" +
            "       pl.construction_type AS type,\n" +
            "       pl.floors_count AS floors,\n" +
            "       SUM(m.price * mp.count) AS price\n" +
            "FROM project pr\n" +
            "         JOIN plan pl ON pr.plan_id = pl.id\n" +
            "         LEFT JOIN material_plan mp on pl.id = mp.plan_id\n" +
            "         LEFT JOIN material m on mp.material_id = m.id\n" +
            "GROUP BY pr.name, pr.start_date, pr.end_date, pl.construction_type, pl.floors_count;\n", nativeQuery = true)
    List<ProjectDTO> findAllProjects();
}
