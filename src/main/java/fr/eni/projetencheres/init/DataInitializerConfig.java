package fr.eni.projetencheres.init;

import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Categorie;
import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dal.ArticleVenduRepository;
import fr.eni.projetencheres.dal.CategorieRepository;
import fr.eni.projetencheres.dal.UtilisateurRepository;
import jakarta.annotation.PostConstruct;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataInitializerConfig {
    @PostConstruct
    public void test() {
    System.out.println("🔥 Classe DataInitializer chargée !");
    }

    @Bean
    CommandLineRunner initData(UtilisateurRepository userRepo,
                               CategorieRepository catRepo,
                               ArticleVenduRepository articleRepo) {

        return args -> {
System.out.println("🔥 DataInitializer exécuté !");
            // 📂 Catégorie
            Categorie cat = catRepo.findByLibelle("Informatique");

            if (cat == null) {
                cat = new Categorie();
                cat.setLibelle("Informatique");
                catRepo.save(cat);
            }
            final String emailCible = "abaille@eni.fr";
            // Si absent, on insère un utilisateur de démo avec des valeurs conformes aux contraintes Bean Validation
            if (userRepo.findByEmail(emailCible).isEmpty()){
                Utilisateur u = new Utilisateur();
                // Pseudo alphanumérique (3-20), sans caractères spéciaux ni espaces
                u.setPseudo("abaille44");
                // Nom/Prénom alphabétiques usuels
                u.setNom("Baille");
                u.setPrenom("Alex");
                u.setEmail(emailCible);
                // Téléphone au format 10 chiffres (sans points/espaces si votre @Pattern l'exige)
                u.setTelephone("0102030405");
                // Adresse/Ville plausibles
                u.setRue("10 Rue des Fleurs");
                u.setVille("Nantes");
                u.setCodePostal(44000);
                // Mot de passe conforme (ex: >=8 chars, lettres+chiffres)
                String password = new BCryptPasswordEncoder().encode("Aa123456");
                u.setMotDePasse(password);
                u.setCredit(0);
                u.setAdministrateur(false);

                userRepo.save(u);

            // 📦 Article
            ArticleVendu article = new ArticleVendu();
            article.setNomArticle("PC Gamer");
            article.setDescription("Très bon PC");
            article.setMiseAPrix(100);
            article.setVendeur(u); // 🔥 obligatoire
            article.setNoCategorie(cat); // 🔥 obligatoire
            article.setDateDebutEncheres(LocalDate.now());
            article.setDateFinEncheres(LocalDate.now().plusDays(7));

            articleRepo.save(article);
            }
            
        };
    }
}