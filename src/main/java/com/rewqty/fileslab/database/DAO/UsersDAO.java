package com.rewqty.fileslab.database.DAO;

import com.rewqty.fileslab.models.UserProfileModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class UsersDAO {

    private final Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }

    public UserProfileModel get(long id) throws HibernateException {
        return session.get(UserProfileModel.class, id);
    }

    public long getUserId(String login) throws HibernateException {
        return session
                .byNaturalId(UserProfileModel.class)
                .using("login", login)
                .load()
                .getId();
    }

    public UserProfileModel getUserByLogin(String login) throws HibernateException {
        return session
                .byNaturalId(UserProfileModel.class)
                .using("login", login)
                .load();
    }

    public UserProfileModel getUserByEmail(String email) throws HibernateException {
        List<UserProfileModel> results = session
                .createSelectionQuery("FROM UserProfileModel WHERE email = :param",
                        UserProfileModel.class)
                .setParameter("param", email)
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public void insertUser(String login, String pass, String email) throws HibernateException {
        session.persist(new UserProfileModel(null, login, pass, email));
    }
}

