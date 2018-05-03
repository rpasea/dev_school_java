package com.example.tweethibernate.repository;

import com.example.tweethibernate.model.User;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAO {
    private static final Logger LOG = LoggerFactory.getLogger(UserDAO.class);

    public List<User> getUsers(Session session) {
        CriteriaQuery<User> query = session.getCriteriaBuilder().createQuery(User.class);
        query.from(User.class);
        return session.createQuery(query).list();
    }

    public Optional<User> getUser(Long id, Session session) {
        return Optional.ofNullable(session.get(User.class, id));
    }

    public Optional<User> getUserByName(String name, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.where(builder.equal(root.get("name"), name));

        return session.createQuery(query).uniqueResultOptional();
    }

    public User insertUser(User user, Session session) {
        Long id = (Long) session.save(user);
        return getUser(id, session).get();
    }

    public void deleteUser(User user, Session session) {
        session.delete(user);
    }
}
