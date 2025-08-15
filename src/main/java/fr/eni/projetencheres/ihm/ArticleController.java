package fr.eni.projetencheres.ihm;

import fr.eni.projetencheres.bll.ArticleVenduService;
import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Categorie;
import fr.eni.projetencheres.dal.ArticleVenduDAO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ArticleController {
	private	final ArticleVenduService articleVenduService;
	private final ArticleVenduDAO articleVenduDAO;

	@GetMapping("/articles")
	public String index(
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(required = false) String categorie, Model model) {

		model.addAttribute("currentPage", page);
		model.addAttribute("size", size);
		model.addAttribute("articles", articleVenduDAO.lstArticles());
		model.addAttribute("categories", List.of(
				new Categorie(1, "Informatique"),
				new Categorie(2, "Ameublement"),
				new Categorie(3, "Vêtements"),
				new Categorie(4, "Sport & Loisirs")
		));
		return "index";
	}

		public ArticleController(ArticleVenduDAO articleVenduDAO, ArticleVenduService articleVenduService) {
			this.articleVenduDAO = articleVenduDAO;
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

//	List<ArticleVendu> articles = articleVenduDAO.findArticles(page, size, sortBy, sortDir);
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
	public String nouvelleVente(Model model){
		List<Categorie> lstCategories = articleVenduService.lstCategories();
		model.addAttribute("categories",lstCategories);
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
