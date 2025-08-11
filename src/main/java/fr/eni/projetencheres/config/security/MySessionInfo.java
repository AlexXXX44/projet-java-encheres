package fr.eni.projetencheres.config.security;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class MySessionInfo {
    private String pseudo;

    public String getCurrentUser() {
        if (pseudo == null) {
            pseudo = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return pseudo;
    }
}
