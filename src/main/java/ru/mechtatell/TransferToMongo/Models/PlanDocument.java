package ru.mechtatell.TransferToMongo.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Map;

@Data
@Document(collection = "plan")
@NoArgsConstructor
@AllArgsConstructor
public class PlanDocument {
    @Id
    private int id;
    private String constructionType;
    private int floorsCount;
    private Map<Integer, Integer> materialMap;
}
