package com.example.tweethibernate.resource;

import com.example.tweethibernate.model.LoginData;
import com.example.tweethibernate.model.User;
import com.example.tweethibernate.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/login")
@ConditionalOnProperty(name = "security.enabled", havingValue = "manual", matchIfMissing = false)
public class LoginResource {
    private static final Logger LOG = LoggerFactory.getLogger(LoginResource.class);
    private static final String SESSION_COOKIE = "JSESSIONID";

    private Map<String, Principal> sessions;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    public LoginResource(Map<String, Principal> sessions, PasswordEncoder passwordEncoder, UserService userService) {
        this.sessions = sessions;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping
    public void login(@RequestBody LoginData loginData, HttpServletResponse response) throws IOException {
        // TODO

        // verify the credentials and create a session

        response.addCookie(new Cookie(SESSION_COOKIE, "teh_session"));
    }
}
