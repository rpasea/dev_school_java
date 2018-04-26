package com.example.httpserver.service;

import com.example.httpserver.TestConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class HttpHandlerIntegrationTest {
    @Autowired
    private Environment environment;

    @Test
    public void test() {
        System.out.println(environment.getProperty("server.root"));
    }
}
