package fr.eni.projetencheres.dal;

import fr.eni.projetencheres.bo.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByPseudo(String pseudo);

    boolean existsByPseudo(String pseudo);

    Utilisateur findByEmail(String email);
}

