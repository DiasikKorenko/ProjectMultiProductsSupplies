package com.exampl.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CheckingAuthorization {
    public boolean check(String login) {
        return SecurityContextHolder.getContext().getAuthentication().getName().equals(login);
    }
}
