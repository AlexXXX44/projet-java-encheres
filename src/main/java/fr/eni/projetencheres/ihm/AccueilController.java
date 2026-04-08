package fr.eni.projetencheres.ihm;

import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Categorie;
import fr.eni.projetencheres.bo.Enchere;
import fr.eni.projetencheres.dal.CategorieRepository;
import fr.eni.projetencheres.dal.EnchereRepository;
import fr.eni.projetencheres.bll.ArticleVenduService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AccueilController {

    private final ArticleVenduService articleVenduService;

    private CategorieRepository categorieRepo;

    private EnchereRepository enchereRepo;
    public AccueilController(ArticleVenduService articleVenduService,
                            CategorieRepository categorieRepo,
                            EnchereRepository enchereRepo
    ) {
        this.articleVenduService = articleVenduService;
        this.categorieRepo = categorieRepo;
        this.enchereRepo = enchereRepo;
    }

    @GetMapping("/")
    public String index(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) Integer noCategorie,
            Model model) {

        //List<ArticleVendu> articles = articleVenduService.search(keyword, noCategorie, currentPage, size);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("size", size);
                model.addAttribute("keyword", keyword);

        //List<Categorie> categories = articleVenduService.findAllCategories();

        List<ArticleVendu> articles = articleVenduService.lstArticles();
        List<Categorie> categories = categorieRepo.findAll();

        // 🔥 AJOUT IMPORTANT
        Map<Integer, List<Enchere>> encheresParArticle = new HashMap<>();

        for (ArticleVendu a : articles) {
            List<Enchere> ench = enchereRepo.findByArticleOrderByMontantEnchereDesc(a);
            encheresParArticle.put(a.getNoArticle(), ench);
        }

        model.addAttribute("articles", articles);
        model.addAttribute("categories", categories);
        model.addAttribute("encheresParArticle", encheresParArticle);

            // Envoie au modèle
        
//         ✅ 4. Injection dans le modèle
        model.addAttribute("categories", categories);
//        model.addAttribute("articles", articles);

        return "index";
    }
}
