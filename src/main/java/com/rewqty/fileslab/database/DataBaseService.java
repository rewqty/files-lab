package com.rewqty.fileslab.database;

import com.rewqty.fileslab.database.DAO.UsersDAO;
import com.rewqty.fileslab.models.UserProfileModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DataBaseService {
    private static final String hibernate_show_sql = "true";

    private static final String hibernate_hbm2ddl_auto = "update";

    private final SessionFactory sessionFactory;

    public DataBaseService() {
        Configuration configuration = getMysqlConfiguration();
        sessionFactory = createSessionFactory(configuration);
    }

    private Configuration getMysqlConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserProfileModel.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");

        configuration.setProperty("hibernate.connection.url",
                "jdbc:mysql://localhost:3306/fileslab?createDatabaseIfNotExist=true");
        configuration.setProperty("hibernate.connection.username", "admin");
        configuration.setProperty("hibernate.connection.password", "admin");

        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public UserProfileModel getUserByLogin(String login) {
        Session session = sessionFactory.openSession();
        UsersDAO dao = new UsersDAO(session);
        UserProfileModel dataSet = dao.getUserByLogin(login);
        session.close();
        return dataSet;
    }

    public UserProfileModel getUserByEmail(String email) {
        Session session = sessionFactory.openSession();
        UsersDAO dao = new UsersDAO(session);
        UserProfileModel dataSet = dao.getUserByEmail(email);
        session.close();
        return dataSet;
    }
    public UserProfileModel getUser(long id) {
        Session session = sessionFactory.openSession();
        UsersDAO dao = new UsersDAO(session);
        UserProfileModel dataSet = dao.get(id);
        session.close();
        return dataSet;
    }
    public void addUser(String login, String pass, String email) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UsersDAO dao = new UsersDAO(session);
        dao.insertUser(login, pass, email);
        transaction.commit();
        session.close();
    }


    /*public boolean columnValueExist(String column, String value) {
        try(ResultSet rs = getStatement().executeQuery(
                String.format(
                        "SELECT %s FROM users WHERE %s = '%s';", column, column, value))){
            while(rs.next()) {
                String tValue = rs.getString(column);
                if (tValue != null) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }*/

    /*public void insertNewUser(String login, String email, String pass) {
        try {
            String sql = String.format(
                    "insert into users (login, email, pass) values ('%s', '%s', '%s');", login, email, pass);
            getStatement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    /*public Statement getStatement() throws SQLException {
        return connection.createStatement();
    }*/
}
