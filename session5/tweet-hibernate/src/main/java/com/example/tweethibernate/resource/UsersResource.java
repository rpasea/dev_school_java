package com.example.tweethibernate.resource;

import com.example.tweethibernate.exception.NotFoundException;
import com.example.tweethibernate.model.User;
import com.example.tweethibernate.repository.UserDAO;
import com.example.tweethibernate.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersResource {
    private UserService userService;

    public UsersResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getTweets() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.insertUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
