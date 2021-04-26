package ru.mechtatell.DAO.JpaReposImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.mechtatell.DAO.Interfaces.ProjectDAO;
import ru.mechtatell.DAO.Repos.ProjectRepos;
import ru.mechtatell.DAO.DTO.ProjectDTO;
import ru.mechtatell.Models.Project;

import java.util.List;
import java.util.Optional;

@Repository
public class ProjectDAOImpl implements ProjectDAO {

    private final ProjectRepos projectRepos;

    @Autowired
    public ProjectDAOImpl(ProjectRepos projectRepos) {
        this.projectRepos = projectRepos;
    }

    @Override
    public int save(Project item) {
        return projectRepos.save(item).getId();
    }

    @Override
    public List<Project> findAll() {
        return (List<Project>) projectRepos.findAll();
    }

    @Override
    public Optional<Project> findById(int id) {
        return projectRepos.findById(id);
    }

    @Override
    public void remove(int id) {
        projectRepos.deleteById(id);
    }

    @Override
    public List<ProjectDTO> findProjectStat() {
        return projectRepos.findAllProjects();
    }
}
