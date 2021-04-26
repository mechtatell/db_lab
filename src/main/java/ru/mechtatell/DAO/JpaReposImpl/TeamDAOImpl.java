package ru.mechtatell.DAO.JpaReposImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.mechtatell.DAO.DTO.TeamDTO;
import ru.mechtatell.DAO.Interfaces.TeamDAO;
import ru.mechtatell.DAO.Repos.TeamRepos;
import ru.mechtatell.Models.Team;

import java.util.List;
import java.util.Optional;

@Repository
public class TeamDAOImpl implements TeamDAO {

    private final TeamRepos teamRepos;

    @Autowired
    public TeamDAOImpl(TeamRepos teamRepos) {
        this.teamRepos = teamRepos;
    }

    @Override
    public int save(Team item) {
        return teamRepos.save(item).getId();
    }

    @Override
    public List<Team> findAll() {
        return (List<Team>) teamRepos.findAll();
    }

    @Override
    public Optional<Team> findById(int id) {
        return teamRepos.findById(id);
    }

    @Override
    public void remove(int id) {
        teamRepos.deleteById(id);
    }

    @Override
    public List<TeamDTO> findTeamStat() {
        return teamRepos.findAllTeams();
    }
}
