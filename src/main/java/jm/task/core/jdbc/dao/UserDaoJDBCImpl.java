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
        String query = "create table UsersTable (" +
                "id BIGINT primary key auto_increment," +
                "name VARCHAR(64)," +
                "lastname VARCHAR(64)," +
                "age TINYINT(3))";
        try (Connection connection = Util.getConnection();
                PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            prepareStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
                PreparedStatement prepareStatement = connection
                        .prepareStatement("drop table UsersTable")) {
            prepareStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "insert into UsersTable (name, lastname, age) values (?, ?, ?)";
        try (Connection connection = Util.getConnection();
                PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            prepareStatement.setString(1, name);
            prepareStatement.setString(2, lastName);
            prepareStatement.setByte(3, age);
            prepareStatement.execute();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
                PreparedStatement prepareStatement = connection
                        .prepareStatement("delete from UsersTable where id = ?")) {
            prepareStatement.setLong(1, id);
            prepareStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = Util.getConnection();
                PreparedStatement prepareStatement = connection
                        .prepareStatement("select * from UsersTable");
                ResultSet rs = prepareStatement.executeQuery()) {
            while (rs.next()) {
                User user = new User(rs.getString("name"),
                        rs.getString("lastName"), rs.getByte("age"));
                user.setId(rs.getLong("id"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement prepareStatement = connection
                     .prepareStatement("truncate table UsersTable")) {
            prepareStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
