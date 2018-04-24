package com.example.httpserver;

import com.example.httpserver.configuration.ContextConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        AbstractApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(ContextConfiguration.class);

        Scanner stdin = new Scanner(System.in);
        System.out.println("Server started, press enter to exit");
        stdin.nextLine();
        System.out.println("Stopping server...");
        applicationContext.close();
    }
}
