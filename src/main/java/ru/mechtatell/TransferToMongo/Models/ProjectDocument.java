package ru.mechtatell.TransferToMongo.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "project")
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDocument {
    @Id
    private int id;
    private String name;
    private Date startDate;
    private Date endDate;
    private List<TeamDocument> teamDocumentList;
    private PlanDocument planDocument;
}
