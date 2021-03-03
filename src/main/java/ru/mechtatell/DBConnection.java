package ru.mechtatell;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DBConnection {
    Connection connection;

    public DBConnection() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://192.168.43.118:5432/db_lab",
                    "mechtatell",
                    "123qwe");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
