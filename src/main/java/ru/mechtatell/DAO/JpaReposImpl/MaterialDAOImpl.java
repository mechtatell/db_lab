package ru.mechtatell.DAO.JpaReposImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.mechtatell.DAO.Interfaces.MaterialDAO;
import ru.mechtatell.DAO.Repos.MaterialRepos;
import ru.mechtatell.Models.Material;

import java.util.List;
import java.util.Optional;

@Repository
public class MaterialDAOImpl implements MaterialDAO {

    private final MaterialRepos materialRepos;

    @Autowired
    public MaterialDAOImpl(MaterialRepos materialRepos) {
        this.materialRepos = materialRepos;
    }

    @Override
    public int save(Material item) {
        return materialRepos.save(item).getId();
    }

    @Override
    public List<Material> findAll() {
        return (List<Material>) materialRepos.findAll();
    }

    @Override
    public Optional<Material> findById(int id) {
        return materialRepos.findById(id);
    }

    @Override
    public void remove(int id) {
        materialRepos.deleteById(id);
    }
}
