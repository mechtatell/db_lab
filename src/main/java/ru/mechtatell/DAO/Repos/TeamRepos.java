package ru.mechtatell.DAO.Repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.mechtatell.DAO.DTO.TeamDTO;
import ru.mechtatell.Models.Team;

import java.util.List;

public interface TeamRepos extends CrudRepository<Team, Integer> {
    @Query(value = "SELECT t.name, COUNT(et.employee_id) AS count, SUM(p.payment) AS sum\n" +
            "FROM team t\n" +
            "         JOIN employee_team et on t.id = et.team_id\n" +
            "         JOIN employee e on et.employee_id = e.id\n" +
            "         JOIN position p on e.position_id = p.id\n" +
            "GROUP BY t.name", nativeQuery = true)
    List<TeamDTO> findAllTeams();
}
