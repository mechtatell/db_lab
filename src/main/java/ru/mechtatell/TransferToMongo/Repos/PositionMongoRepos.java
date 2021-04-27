package ru.mechtatell.TransferToMongo.Repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.mechtatell.TransferToMongo.Models.PositionDocument;

public interface PositionMongoRepos extends MongoRepository<PositionDocument, Integer> {
}
