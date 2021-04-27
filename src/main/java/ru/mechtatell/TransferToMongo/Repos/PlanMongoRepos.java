package ru.mechtatell.TransferToMongo.Repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.mechtatell.TransferToMongo.Models.PlanDocument;

public interface PlanMongoRepos extends MongoRepository<PlanDocument, Integer> {
}
