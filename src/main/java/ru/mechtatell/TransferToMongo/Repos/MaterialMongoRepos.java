package ru.mechtatell.TransferToMongo.Repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.mechtatell.TransferToMongo.Models.MaterialDocument;

public interface MaterialMongoRepos extends MongoRepository<MaterialDocument, Integer> {
}
