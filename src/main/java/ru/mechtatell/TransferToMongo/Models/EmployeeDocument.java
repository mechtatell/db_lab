package ru.mechtatell.TransferToMongo.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@Document(collection = "employee")
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDocument {
    @Id
    private int id;
    private String firstName;
    private String lastName;
    private PositionDocument positionDocument;
}
