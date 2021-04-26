package ru.mechtatell.DAO.Interfaces;

import ru.mechtatell.DAO.DTO.ProjectDTO;
import ru.mechtatell.Models.Project;

import java.util.List;

public interface ProjectDAO extends DAO<Project> {
    List<ProjectDTO> findProjectStat();
}
