package fr.eni.projetencheres.ihm;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import fr.eni.projetencheres.bll.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.eni.projetencheres.bll.ArticleVenduService;
import fr.eni.projetencheres.bll.EnchereService;
import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Categorie;
import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dal.UtilisateurRepository;

@Controller
@RequestMapping("/encheres")
public class EncheresController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private ArticleVenduService articleVenduService;

    @Autowired
    private EnchereService enchereService;

    @GetMapping("/encheres")
    public String accueil(Model model) {
        List<ArticleVendu> lstArticles = articleVenduService.lstArticles();
        List<Utilisateur> lstUtilisateurs = utilisateurService.findAll();
        List<Categorie> lstCategories = articleVenduService.findAllCategories();
        model.addAttribute("categories", lstCategories);
        for (Utilisateur u : lstUtilisateurs) {
            for (int j = 0; j < lstArticles.size(); j++) {
                ArticleVendu a = articleVenduService.lstArticles().get(j);
                a.setVendeur(u);
                for (Categorie c : lstCategories) {
                    if (articleVenduService.trouveCategorieParNo((Integer) a.getNoArticle()).equals(c.getLibelle())
                            && articleVenduService.trouvePseudoParNo((Integer) a.getNoArticle()).equals(u.getPseudo())) {
                        a.setCategorie(c);
                        model.addAttribute("articleVendu", a);
                    }
                }
            }
        }

        List<ArticleVendu> articles = articleVenduService.trouveParCat(1);
        // Affiche tous les articles
		for (ArticleVendu article : articles) {
			System.out.println(article);
		}
        return "index";
    }

@PostMapping("/article")
public String faireEnchere(@RequestParam int noArticle,
                           @RequestParam int montantEncheres,
                           Principal principal,
                           RedirectAttributes redirectAttributes) {

    try {
        Utilisateur utilisateur = utilisateurService.findByEmail(principal.getName());
        utilisateur = utilisateurService.findByEmailOrThrow(principal.getName());
    
        ArticleVendu article = articleVenduService.findById(noArticle);
        enchereService.faireEnchere(utilisateur, article, montantEncheres);
        redirectAttributes.addFlashAttribute("message", "Enchère réussie !");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());
        return "redirect:/articles/" + noArticle; // Redirige vers la page de l'article en cas d'erreur
    }

    return "redirect:/articles";
}

}
