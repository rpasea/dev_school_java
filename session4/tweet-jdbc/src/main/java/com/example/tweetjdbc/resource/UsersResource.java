package com.example.tweetjdbc.resource;

import com.example.tweetjdbc.exception.NotFoundException;
import com.example.tweetjdbc.model.User;
import com.example.tweetjdbc.repository.UserDAO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersResource {
    private UserDAO userDAO;

    public UsersResource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping
    public List<User> getTweets() {
        return userDAO.getUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userDAO.getUser(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userDAO.insertUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userDAO.deleteUser(id);
    }
}
