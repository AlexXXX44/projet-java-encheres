package fr.eni.projetencheres.ihm;

import fr.eni.projetencheres.bll.ArticleVenduService;
import fr.eni.projetencheres.bll.EnchereService;
import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Categorie;
import fr.eni.projetencheres.bo.Enchere;
import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dal.ArticleVenduDAO;
import fr.eni.projetencheres.dal.ArticleVenduRepository;
import fr.eni.projetencheres.dal.CategorieRepository;
import fr.eni.projetencheres.dal.EnchereRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @Autowired
    private EnchereService enchereService;

    @GetMapping
    public String listerArticles(@RequestParam(required = false) String keyword,
                                 @RequestParam(required = false) Integer noCategorie,
                                 Model model) {
        List<ArticleVendu> articles = articleRepo.search(keyword, noCategorie);
        List<Categorie> categories = categorieRepo.findAll();

        // Charger les enchères existantes par article
        Map<Long, List<Enchere>> encheresParArticle = new HashMap<>();
        List<Enchere> ench = null;
        for (ArticleVendu a : articles) {
            ench = enchereRepo.findByArticleOrderByMontantEnchereDesc(a);
            encheresParArticle.put((long) a.getNoArticle(), ench);
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
    //@PostMapping("/enchere")
    //public String faireEnchere(@AuthenticationPrincipal Utilisateur utilisateur,
    //                           @RequestParam Integer noUtilisateur,
    //                           @RequestParam Integer noArticle,
    //                           @RequestParam int montantEnchere,
    //                           @RequestParam(required = false) String keyword,
    //                           @RequestParam(required = false) Integer noCategorie,
    //                           RedirectAttributes redirectAttrs) {
    //    try {
    //        enchereService.faireEnchere(utilisateur.getNoUtilisateur(), noArticle, montantEnchere);
    //        redirectAttrs.addFlashAttribute("message", "Enchère réussie !");
    //    } catch (Exception e) {
    //        redirectAttrs.addFlashAttribute("error", e.getMessage());
    //    }

    //    String url = "redirect:/articles";
    //    if (keyword != null || noCategorie != null) {
    //        url += "?";
    //        if (keyword != null) url += "keyword=" + keyword + "&";
    //        if (noCategorie != null) url += "categorieId=" + noCategorie;
    //    }
    //    return url;
    //}

    private final ArticleVenduService articleVenduService;

    @GetMapping("/article/{id}")
    public String afficherDetails(@PathVariable("id") int id, Model model) {
        ArticleVendu article = articleVenduService.findById(id);
        if (article == null) {
            // redirection si article introuvable
            return "redirect:/";
        }
        model.addAttribute("article", article);
        return "article-details"; // nom du fichier HTML dans templates
    }

//	@GetMapping("/article/{id}")
//	public String afficherDetails(@PathVariable("id") int id, Model model) {
//		ArticleVendu article = articleVenduService.getArticleById(id);
//		model.addAttribute("article", article);
//		return "details"; // Thymeleaf view
//	}

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

//	public String index(
//			@RequestParam(defaultValue = "1") int page,
//			@RequestParam(defaultValue = "5") int size,
//			@RequestParam(required = false) String categorie, Model model) {
//			 Récupère toutes les catégories
//			List<Categorie> categories = articleVenduDAO.findAllCategories();
//			model.addAttribute("categories", categories);

// Si catégorie choisie, on filtre
//			List<ArticleVendu> articles;
//			if (categorie != null && !categorie.isEmpty()) {
//				articles = articleVenduDAO.findByCat(categorie);
//			} else {
//				articles = articleVenduDAO.lstArticles();
//			}
//			model.addAttribute("articles", articles);
//			model.addAttribute("currentPage", page);
//			model.addAttribute("size", size);
//
//			return "index";
//		}


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

//	List<ArticleVendu> articles = repository.findArticles(page, size, sortBy, sortDir);
//		model.addAttribute("articles", articles);
        // Ajoute aussi pagination info (page courante, taille, etc.) au modèle
//		model.addAttribute("currentPage", page);
//		model.addAttribute("pageSize", size);
//		model.addAttribute("sortBy", sortBy);
//		model.addAttribute("sortDir", sortDir);
//
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
            System.out.println(a);
            model.addAttribute("article", a);
            return "/";
        }
        return null;
    }

    /*
     * @PostMapping("/ajout") String ajout(@ModelAttribute("article") @Valid
     * ArticleVendu article, BindingResult br, Model model) throws MetierException {
     * String retour = "redirect:/"; if (br.hasErrors()) { retour = "creerArticle";
     * model.addAttribute("article", article); } else {
     * articleService.ajouterArticleVendu(article); } return retour; }
     */
}