package ru.mechtatell.TransferToMongo.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Data
@Document(collection = "team")
@NoArgsConstructor
@AllArgsConstructor
public class TeamDocument {
    @Id
    private int id;
    private String name;
    private List<EmployeeDocument> employeeDocumentList;
}
