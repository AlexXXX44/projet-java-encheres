package fr.eni.projetencheres.ihm;

import fr.eni.projetencheres.bll.ArticleVenduService;
import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Categorie;
import fr.eni.projetencheres.bo.Enchere;
import fr.eni.projetencheres.dal.ArticleVenduRepository;
import fr.eni.projetencheres.dal.CategorieRepository;
import fr.eni.projetencheres.dal.EnchereRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleVenduRepository articleRepo;

    @Autowired
    private CategorieRepository categorieRepo;

    @Autowired
    private EnchereRepository enchereRepo;
    @GetMapping
    public String listerArticles(@RequestParam(required = false) String keyword,
                                 @RequestParam(required = false) Integer noCategorie,
                                 Model model) {
        List<ArticleVendu> articles = articleRepo.search(keyword, noCategorie);
        List<Categorie> categories = categorieRepo.findAll();

        // Charger les enchères existantes par article
        Map<Integer, List<Enchere>> encheresParArticle = new HashMap<>();
        List<Enchere> ench = null;
        for (ArticleVendu a : articles) {
            ench = enchereRepo.findByArticleOrderByMontantEnchereDesc(a);
            encheresParArticle.put(a.getNoArticle(), ench);
        }

        model.addAttribute("articles", articles);
        model.addAttribute("categories", categories);
        model.addAttribute("keyword", keyword);
        model.addAttribute("noCategorie", noCategorie);
        model.addAttribute("ench", ench);

        model.addAttribute("encheresParArticle", encheresParArticle);

        return "articles";
    }

    // ✅ Nouvelle méthode pour enchérir
    private final ArticleVenduService articleVenduService;

    @GetMapping("/{id}")
    public String afficherDetails(@PathVariable("id") int id, Model model) {
        ArticleVendu article = articleVenduService.findById(id);
        if (article == null) {
            // redirection si article introuvable
            return "redirect:/";
        }
        model.addAttribute("article", article);
        return "article-details"; // nom du fichier HTML dans templates
    }

    @GetMapping("/articles")
    public String index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String categorie, Model model) {

        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("articles", articleVenduService.lstArticles());
        model.addAttribute("categories", List.of(
                new Categorie(1, "Informatique"),
                new Categorie(2, "Ameublement"),
                new Categorie(3, "Vêtements"),
                new Categorie(4, "Sport & Loisirs")
        ));
        return "index";
    }

    public ArticleController(ArticleVenduService articleVenduService) {
        this.articleVenduService = articleVenduService;
    }

    @GetMapping("/test")
    public String test(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "date_fin_encheres") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {

        List<ArticleVendu> articles = articleVenduService.getArticles(page, size, sortBy, sortDir);

        model.addAttribute("articles", articles);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);

        // Categories + autres attributs

        return "index";
    }

    @GetMapping("/supprimer_article")
    public String supprimerArticle(ArticleVendu nomArticle) {
        articleVenduService.supprimerArticleVendu(nomArticle);
        return "redirect:/logout";
    }

    @GetMapping("/nouvelle_vente")
    public String nouvelleVente(Model model) {
        List<Categorie> lstCategories = articleVenduService.findAllCategories();
        model.addAttribute("categories", lstCategories);
        return "nouvelleVente";
    }

    @GetMapping("/modifier_article")
    public String modifierArticle(Model model, int noArticle) {
        ArticleVendu a = articleVenduService.trouveParNo(noArticle);
        model.addAttribute("article", a);
        return "modifArticle";
    }

    @PostMapping("/enregistrer_article")
    public String enregistrerarticle(@Valid ArticleVendu a, BindingResult br, Model model) {

        if (br.hasErrors()) {
            model.addAttribute("article", a);
        } else {
            articleVenduService.modifierArticleVendu(a);
            model.addAttribute("article", a);
            return "/";
        }
        return null;
    }
}