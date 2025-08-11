package fr.eni.projetencheres.ihm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import fr.eni.projetencheres.bll.ArticleVenduService;
import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Categorie;
import jakarta.validation.Valid;

@Controller
public class ArticleController {

	@Autowired
	private ArticleVenduService articleService;

	@GetMapping("/supprimer_article")
	public String supprimerArticle(ArticleVendu nomArticle) {
		articleService.supprimerArticleVendu(nomArticle);
		return "redirect:/logout";
	}

	@GetMapping("/nouvelle_vente")
	public String nouvelleVente(Model model){
		List<Categorie> lstCategories = articleService.lstCategories();
		model.addAttribute("categories",lstCategories);
		return "nouvelleVente";
	}

	@GetMapping("/modifier_article")
	public String modifierArticle(Model model, int noArticle) {
		ArticleVendu a = articleService.trouveParNo(noArticle);
		model.addAttribute("article", a);
		return "modifArticle";
	}

	@PostMapping("/enregistrer_article")
	public String enregistrerarticle(@Valid ArticleVendu a, BindingResult br, Model model) {

		if (br.hasErrors()) {
			model.addAttribute("article", a);
		} else {
			articleService.modifierArticleVendu(a);
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
