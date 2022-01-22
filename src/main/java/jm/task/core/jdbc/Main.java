package jm.task.core.jdbc;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        //Создание таблицы
        userService.createUsersTable();
        //Добавление пользователей
        userService.saveUser("Ivan", "Ivanov", (byte)23);
        userService.saveUser("Stasy", "Stasova", (byte)30);
        userService.saveUser("Petr", "Petrov", (byte)35);
        userService.saveUser("Sidor", "Sidorov", (byte)37);
        //Печать пользователей
        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }
        //Очистка таблицы
        userService.cleanUsersTable();
        //Удаление таблицы
        userService.dropUsersTable();
    }
}

