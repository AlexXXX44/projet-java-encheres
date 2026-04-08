package fr.eni.projetencheres.init;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Categorie;
import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dal.ArticleVenduRepository;
import fr.eni.projetencheres.dal.CategorieRepository;
import fr.eni.projetencheres.dal.UtilisateurRepository;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolationException;

public class DataInitializerConfig {

    //@Bean
    CommandLineRunner initData(UtilisateurRepository userRepo,
                               CategorieRepository catRepo,
                               ArticleVenduRepository articleRepo) {

        return args -> {

            // 📂 Catégorie
            Categorie cat = catRepo.findByLibelle("Informatique");

            if (cat == null) {
                cat = new Categorie();
                cat.setLibelle("Informatique");
                catRepo.save(cat);
            }

            final String emailCible = "abaille@eni.fr";

            // 🔥 Vérification utilisateur
            Utilisateur existingUser = userRepo.findByEmail(emailCible);

            if (existingUser == null) {

                Utilisateur u = new Utilisateur();

                u.setPseudo("alex44");
                u.setNom("Baille");
                u.setPrenom("Alex");
                u.setEmail(emailCible);

                // ✔ nettoyage + cohérence regex
                String tel = "01.02.03.04.05".replaceAll("\\D", "");
                u.setTelephone(tel);

                u.setRue("10 Rue des Fleurs");
                u.setVille("Nantes");
                u.setCodePostal(44000);

                String password = new BCryptPasswordEncoder().encode("Aa123456");
                u.setMotDePasse(password);

                u.setCredit(1000); // 💡 mieux pour tester les enchères
                u.setAdministrateur(false);

                try {

                    userRepo.save(u);

                } catch (ConstraintViolationException e) {
                    e.getConstraintViolations().forEach(v -> {
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 📦 Article
                ArticleVendu article = new ArticleVendu();
                article.setNomArticle("PC Gamer");
                article.setDescription("Très bon PC");
                article.setMiseAPrix(100);
                article.setVendeur(u);
                article.setNoCategorie(cat);
                article.setDateDebutEncheres(LocalDate.now());
                article.setDateFinEncheres(LocalDate.now().plusDays(7));

                articleRepo.save(article);                 
            }
        };
    }
}