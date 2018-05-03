package com.example.tweethibernate.security;

import org.eclipse.jetty.server.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;

@Component
@ConditionalOnProperty(name = "security.enabled", havingValue = "manual")
public class SessionInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(SessionInterceptor.class);
    private static final String SESSION_COOKIE = "JSESSIONID";

    private Map<String, Principal> sessions;

    public SessionInterceptor(Map<String, Principal> sessions) {
        this.sessions = sessions;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURI().equals("/login")) {
            return true;
        }

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                String cookieName = cookie.getName().trim().toUpperCase();
                if (cookieName.equals(SESSION_COOKIE)) {
                    String sessionId = cookie.getValue();
                    Principal principal = sessions.get(sessionId);
                    if (principal == null) {
                        break;
                    }

                    ((Request) request).setAuthentication(new SimpleAuthentication(principal));

                    return true;
                }
            }
        }

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not authorized");
        return false;
    }
}
