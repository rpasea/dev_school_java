package com.example.tweethibernate.security;

import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.UserIdentity;

import javax.security.auth.Subject;
import java.security.Principal;

public class SimpleAuthentication implements Authentication.User {
    private Principal principal;

    public SimpleAuthentication(Principal principal) {
        this.principal = principal;
    }

    @Override
    public String getAuthMethod() {
        return "simple";
    }

    @Override
    public UserIdentity getUserIdentity() {
        return new UserIdentity() {
            @Override
            public Subject getSubject() {
                return null;
            }

            @Override
            public Principal getUserPrincipal() {
                return principal;
            }

            @Override
            public boolean isUserInRole(String s, Scope scope) {
                return false;
            }
        };
    }

    @Override
    public boolean isUserInRole(UserIdentity.Scope scope, String s) {
        return false;
    }

    @Override
    public void logout() {

    }
}
