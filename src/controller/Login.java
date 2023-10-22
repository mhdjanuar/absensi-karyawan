package controller;

import java.sql.*;
import models.User;
public class Login {
    private static final String DB_URL = "jdbc:sqlite:D:\\Study\\Java\\sqlite\\absensi.db";

    public User login(String username, String password) {
        User user = null;
        String query = "SELECT * FROM Users WHERE Username = ? AND Password = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getInt("ID"));
                    user.setUserName(resultSet.getString("Username"));
                    user.setDepartment(resultSet.getString("department"));
                    user.setRole(resultSet.getString("role"));
                    user.setGaji_harian(resultSet.getInt("gaji_harian"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
