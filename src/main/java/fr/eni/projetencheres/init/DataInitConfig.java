package fr.eni.projetencheres.init;

import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dal.UtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitConfig {

    @Bean
    CommandLineRunner init(UtilisateurRepository repo /*, PasswordEncoder encoder*/) {
        return args -> {
            final String emailCible = "abaille@campus-eni.fr"; // ex: remplacez par votre email si besoin

            // Si absent, on insère un utilisateur de démo avec des valeurs conformes aux contraintes Bean Validation
            if (repo.findByEmail(emailCible) == null) {
                Utilisateur u = new Utilisateur();
                // Pseudo alphanumérique (3-20), sans caractères spéciaux ni espaces
                u.setPseudo("abaille44");
                // Nom/Prénom alphabétiques usuels
                u.setNom("Baille");
                u.setPrenom("Alex");
                u.setEmail(emailCible);
                // Téléphone au format 10 chiffres (sans points/espaces si votre @Pattern l'exige)
                u.setTelephone("01.02.03.04.05");
                // Adresse/Ville plausibles
                u.setRue("10 Rue des Fleurs");
                u.setVille("Nantes");
                u.setCodePostal(44000);
                // Mot de passe conforme (ex: >=8 chars, lettres+chiffres)
                // u.setMotDePasse(encoder.encode("Aa123456")); // si vous activez un PasswordEncoder
                u.setMotDePasse("Aa123456"); // à chiffrer en pratique
                u.setCredit(0);
                u.setAdministrateur(false);

                repo.save(u);
            }
        };
    }
}