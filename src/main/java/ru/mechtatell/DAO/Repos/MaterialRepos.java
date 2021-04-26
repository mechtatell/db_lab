package ru.mechtatell.DAO.Repos;

import org.springframework.data.repository.CrudRepository;
import ru.mechtatell.Models.Material;

public interface MaterialRepos extends CrudRepository<Material, Integer> {
}
