package ru.mechtatell.DAO.Interfaces;

import ru.mechtatell.DAO.DTO.TeamDTO;
import ru.mechtatell.Models.Team;

import java.util.List;

public interface TeamDAO extends DAO<Team> {
    List<TeamDTO> findTeamStat();
}
