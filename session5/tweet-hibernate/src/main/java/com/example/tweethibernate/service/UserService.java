package com.example.tweethibernate.service;


import com.example.tweethibernate.model.User;
import com.example.tweethibernate.repository.ProgrammaticTransactionManager;
import com.example.tweethibernate.repository.UserDAO;
import org.hibernate.Session;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private UserDAO userDAO;
    private ProgrammaticTransactionManager transactionManager;

    public UserService(UserDAO userDAO, ProgrammaticTransactionManager transactionManager) {
        this.userDAO = userDAO;
        this.transactionManager = transactionManager;
    }

    public List<User> getUsers() {
        return transactionManager.doInTransaction(new ProgrammaticTransactionManager.BusinessTransaction<List<User>>() {
            @Override
            public List<User> execute(Session session) {
                return userDAO.getUsers(session);
            }
        });
    }

    public Optional<User> getUser(Long id) {
        return transactionManager.doInTransaction(new ProgrammaticTransactionManager.BusinessTransaction<Optional<User>>() {
            @Override
            public Optional<User> execute(Session session) {
                return userDAO.getUser(id, session);
            }
        });
    }

    public User insertUser(User user) {
        return transactionManager.doInTransaction(new ProgrammaticTransactionManager.BusinessTransaction<User>() {
            @Override
            public User execute(Session session) {
                return userDAO.insertUser(user, session);
            }
        });
    }

    public void deleteUser(Long userId) {
        transactionManager.doInTransaction(new ProgrammaticTransactionManager.BusinessTransaction<Void>() {
            @Override
            public Void execute(Session session) {
                Optional<User> user = userDAO.getUser(userId, session);
                if (user.isPresent()) {
                    userDAO.deleteUser(user.get(), session);
                }
                return null;
            }
        });
    }

    public Optional<User> getUserByName(String name) {
        return transactionManager.doInTransaction(new ProgrammaticTransactionManager.BusinessTransaction<Optional<User>>() {
            @Override
            public Optional<User> execute(Session session) {
                return userDAO.getUserByName(name, session);
            }
        });
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<User> user = transactionManager.doInTransaction(new ProgrammaticTransactionManager.BusinessTransaction<Optional<User>>() {
            @Override
            public Optional<User> execute(Session session) {
                return userDAO.getUserByName(name, session);
            }
        });

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new org.springframework.security.core.userdetails.User(user.get().getName(), user.get().getPassword(),
            new LinkedList<>());
    }


}
