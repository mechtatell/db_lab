package ru.mechtatell.DAO.JpaReposImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.mechtatell.DAO.Interfaces.PositionDAO;
import ru.mechtatell.DAO.Repos.PositionRepos;
import ru.mechtatell.Models.Position;

import java.util.List;
import java.util.Optional;

@Repository
public class PositionDAOImpl implements PositionDAO {

    private final PositionRepos positionRepos;

    @Autowired
    public PositionDAOImpl(PositionRepos positionRepos) {
        this.positionRepos = positionRepos;
    }

    @Override
    public int save(Position item) {
        return positionRepos.save(item).getId();
    }

    @Override
    public List<Position> findAll() {
        return (List<Position>) positionRepos.findAll();
    }

    @Override
    public Optional<Position> findById(int id) {
        return positionRepos.findById(id);
    }

    @Override
    public void remove(int id) {
        positionRepos.deleteById(id);
    }
}
