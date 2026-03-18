package fr.eni.projetencheres.dal;

import fr.eni.projetencheres.bo.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    // Recherche d’un utilisateur via son pseudo
    // (optionnel) Si tu veux garder la recherche par email aussi
    Optional<Utilisateur> findByEmail(String email);
    //Optional<Utilisateur> findByUsername(String username);
    Utilisateur findByPseudo(String pseudo);
    boolean existsByPseudo(String pseudo);
}
