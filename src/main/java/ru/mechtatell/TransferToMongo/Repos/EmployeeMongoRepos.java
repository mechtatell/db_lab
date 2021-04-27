package ru.mechtatell.TransferToMongo.Repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.mechtatell.TransferToMongo.Models.EmployeeDocument;

public interface EmployeeMongoRepos extends MongoRepository<EmployeeDocument, Integer> {
}
