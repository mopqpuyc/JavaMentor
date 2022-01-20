package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String sql = "create table UsersTable (" +
                "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(64)," +
                "lastname VARCHAR(64)," +
                "age TINYINT(3))";
        Transaction transaction = null;
        try (Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).addEntity(User.class);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String table = "UsersTable";
        String sql = "drop table " + table;
        Transaction transaction = null;
        try (Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(new User(name, lastName, age));
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            User user = new User();
            user.setId(id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
        Transaction transaction = null;
        try (Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            list = session.createQuery("from User").list();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        String table = "UsersTable";
        String query = "truncate table " + table;
        Transaction transaction = null;
        try (Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(query).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
