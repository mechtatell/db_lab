package ru.mechtatell.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mechtatell.DBConnection;
import ru.mechtatell.models.Position;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component("PositionDAO")
public class PositionDAO implements DAO<Position> {

    @Autowired
    private DBConnection connection;

    public List<Position> index() {
        List<Position> positions = new ArrayList<>();

        try {
            Statement statement = connection.getConnection().createStatement();
            String SQL = "SELECT * FROM position";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Position position = new Position();
                position.setId(resultSet.getInt("id"));
                position.setName(resultSet.getString("name"));
                position.setPayment(Double.parseDouble(resultSet.getString("payment")));

                positions.add(position);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return positions;
    }

    public void save(Position position) {
        try {
            PreparedStatement statement =
                    connection.getConnection().prepareStatement("INSERT INTO position (name, payment) VALUES (?, ?)");

            statement.setString(1, position.getName());
            statement.setDouble(2, position.getPayment());

            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Position show(int id) {
        Position position = null;
        try {
            PreparedStatement statement =
                    connection.getConnection().prepareStatement("SELECT * FROM position WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            position = new Position();
            position.setId(resultSet.getInt("id"));
            position.setName(resultSet.getString("name"));
            position.setPayment(resultSet.getDouble("payment"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return position;
    }

    public void update(int id, Position updatedPosition) {
        try {
            PreparedStatement statement =
                    connection.getConnection().prepareStatement("UPDATE position SET name=?, payment=? WHERE id=?");
            statement.setString(1, updatedPosition.getName());
            statement.setDouble(2, updatedPosition.getPayment());
            statement.setInt(3, id);

            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void remove(int id) {
        try {
            PreparedStatement statement =
                    connection.getConnection().prepareStatement("DELETE FROM position WHERE id=?");
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
