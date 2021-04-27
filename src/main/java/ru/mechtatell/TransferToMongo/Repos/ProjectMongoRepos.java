package ru.mechtatell.TransferToMongo.Repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.mechtatell.TransferToMongo.Models.ProjectDocument;

public interface ProjectMongoRepos extends MongoRepository<ProjectDocument, Integer> {
}
