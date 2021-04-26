package ru.mechtatell.DAO.DTO;

import java.sql.Timestamp;

public interface ProjectDTO {
    String getName();

    Timestamp getStart();

    Timestamp getEnd();

    String getType();

    int getFloors();

    double getPrice();
}
