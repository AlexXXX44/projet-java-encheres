package fr.eni.projetencheres.ihm;

import fr.eni.projetencheres.bo.Categorie;
import fr.eni.projetencheres.bll.ArticleVenduService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AccueilController {

    private final ArticleVenduService articleVenduService;

    public AccueilController(ArticleVenduService articleVenduService) {
        this.articleVenduService = articleVenduService;
    }

    @GetMapping("/")
    public String index(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) Integer noCategorie,
            Model model) {

        var articles = articleVenduService.search(keyword, noCategorie, currentPage, size);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("size", size);
        model.addAttribute("articles", articles);
        model.addAttribute("keyword", keyword);


//    ✅ 1. Catégories fictives
//        List<Categorie> categories = new ArrayList<>();

        // ✅ 2. Utilisateurs fictifs
//        Utilisateur u1 = new Utilisateur();
//        u1.setPseudo("Jean75");
//        Utilisateur u2 = new Utilisateur();
//        u2.setPseudo("Claire92");

        // ✅ 3. Articles fictifs
//        List<ArticleVendu> articles = new ArrayList<>();
//   Simule ou récupère des données
//        List<ArticleVendu> articles = articleVenduService.lstArticles();  // ou méthode findByCat("Informatique") pour tester
        List<Categorie> categories = articleVenduService.findAllCategories();

        // Envoie au modèle
//        model.addAttribute("articles", articles);
//         ✅ 4. Injection dans le modèle
        model.addAttribute("categories", categories);
//        model.addAttribute("articles", articles);

        return "index";
    }
}
