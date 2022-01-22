package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        try {
            Configuration configuration = new Configuration()
                    .setProperty(Environment.DRIVER, "com.mysql.cj.jdbc.Driver")
                    .setProperty(Environment.URL, URL)
                    .setProperty(Environment.USER, USERNAME)
                    .setProperty(Environment.PASS, PASSWORD)
                    .setProperty(Environment.HBM2DDL_AUTO, "update")
                    .addAnnotatedClass(User.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }
}
