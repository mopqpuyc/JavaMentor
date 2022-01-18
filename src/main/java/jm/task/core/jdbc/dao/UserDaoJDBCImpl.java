package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String query = "create table Users (" +
                "id BIGINT primary key auto_increment," +
                "name VARCHAR(64)," +
                "lastname VARCHAR(64)," +
                "age TINYINT(3))";
        try (Connection connection = Util.getConnection();
                PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            prepareStatement.execute();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1050) {
                System.out.println(e.getMessage());
            } else {
                e.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        String query = "drop table Users";
        try (Connection connection = Util.getConnection();
                PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            prepareStatement.execute();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1051) {
                System.out.println(e.getMessage());
            } else {
                e.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "insert into Users (name, lastname, age) values (?, ?, ?)";
        try (Connection connection = Util.getConnection();
                PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            prepareStatement.setString(1, name);
            prepareStatement.setString(2, lastName);
            prepareStatement.setByte(3, age);
            prepareStatement.execute();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            if (e.getErrorCode() == 1146) {
                System.out.println(e.getMessage());
            } else {
                e.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        String query = "delete from Users where id = ?";
        try (Connection connection = Util.getConnection();
                PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            prepareStatement.setLong(1, id);
            prepareStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String query = "select * from Users";

        try (Connection connection = Util.getConnection();
                PreparedStatement prepareStatement = connection.prepareStatement(query);
                ResultSet rs = prepareStatement.executeQuery()) {

            while (rs.next()) {
                User user = new User(rs.getString("name"),
                        rs.getString("lastName"), rs.getByte("age"));
                user.setId(rs.getLong("id"));
                userList.add(user);
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1146) {
                System.out.println(e.getMessage());
            } else {
                e.printStackTrace();
            }
        }
        return userList;
    }

    public void cleanUsersTable() {
        String query = "truncate table Users";
        try (Connection connection = Util.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            prepareStatement.execute();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1146) {
                System.out.println(e.getMessage());
            } else {
                e.printStackTrace();
            }
        }
    }
}
