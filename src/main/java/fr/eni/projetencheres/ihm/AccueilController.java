package fr.eni.projetencheres.ihm;

import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Categorie;
import fr.eni.projetencheres.bo.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AccueilController {
    @GetMapping("/accueil")
    public String index(Model model) {

    // ✅ 1. Catégories fictives
        List<Categorie> categories = new ArrayList<>();
        categories.add(new Categorie(1, "Informatique"));
        categories.add(new Categorie(2, "Ameublement"));
        categories.add(new Categorie(3, "Vêtements"));
        categories.add(new Categorie(4, "Sport & Loisirs"));

        // ✅ 2. Utilisateurs fictifs
        Utilisateur u1 = new Utilisateur();
        u1.setPseudo("Jean75");
        Utilisateur u2 = new Utilisateur();
        u2.setPseudo("Claire92");

        // ✅ 3. Articles fictifs
        List<ArticleVendu> articles = new ArrayList<>();
        articles.add(new ArticleVendu(1, "PC Gamer", "Tour haut de gamme", "EN_COURS", LocalDate.now(), LocalDate.now().plusDays(5),
                        1200, 1200, u1, categories.get(0)));
        articles.add(new ArticleVendu(2, "Canapé 3 places", "Confort garanti", "EN_COURS", LocalDate.now(), LocalDate.now().plusDays(10),
                        350, 350, u2, categories.get(1)));
        articles.add(new ArticleVendu(3, "Vélo route", "Idéal pour débutant", "EN_COURS", LocalDate.now(), LocalDate.now().plusDays(3),
                        500, 500, u1, categories.get(3)));
//        "https://picsum.photos/seed/velo/400/300", categories.get(3), u1));
//        articles.add(new ArticleVendu(4, "Pull en laine", "Neuf et chaud", LocalDate.now().plusDays(8),
//                60, "https://picsum.photos/seed/pull/400/300", categories.get(2), u2));
        // Simule ou récupère des données
//        List<ArticleVendu> articles = articleVenduService.lstArticles();  // ou méthode findByCat("Informatique") pour tester
//        List<Categorie> categories = categorieService.findAllCategories();

        // Envoie au modèle
//        model.addAttribute("articles", articles);
//        model.addAttribute("categories", categories);
        // ✅ 4. Injection dans le modèle
        model.addAttribute("categories", categories);
        model.addAttribute("articles", articles);

        return "index";
    }
}
