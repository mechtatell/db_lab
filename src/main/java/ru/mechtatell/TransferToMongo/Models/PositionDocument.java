package ru.mechtatell.TransferToMongo.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@Document(collection = "position")
@NoArgsConstructor
@AllArgsConstructor
public class PositionDocument {
    @Id
    private int id;
    private String name;
    private double payment;
}
