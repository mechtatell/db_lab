package ru.mechtatell.TransferToMongo.Repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.mechtatell.TransferToMongo.Models.TeamDocument;

public interface TeamMongoRepos extends MongoRepository<TeamDocument, Integer> {
}
