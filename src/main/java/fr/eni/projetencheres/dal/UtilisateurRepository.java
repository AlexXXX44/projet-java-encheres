package fr.eni.projetencheres.dal;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UtilisateurRepository {
    public Optional<Object> findByPseudo(String username) {
        return Optional.empty();
    }
}
