package fr.eni.projetencheres.ihm;

import java.security.Principal;
import java.util.List;

import fr.eni.projetencheres.bll.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.eni.projetencheres.bll.ArticleVenduService;
import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Categorie;
import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.exception.MetierException;
import jakarta.validation.Valid;

@Controller
public class EncheresController {

	@Autowired
	private UtilisateurService utilisateurService;

	@Autowired
	private ArticleVenduService articleVenduService;

	@GetMapping("/encheres")
	public String accueil(Model model) {
		List<ArticleVendu> lstArticles = articleVenduService.lstArticlesVendus();
		List<Utilisateur> lstUtilisateurs = utilisateurService.findAll();
		List<Categorie> lstCategories = articleVenduService.lstCategories();
		model.addAttribute("categories", lstCategories);
        for (Utilisateur u : lstUtilisateurs) {
            for (int j = 0; j < lstArticles.size(); j++) {
//				System.out.println(u.getPseudo());
                ArticleVendu a = articleVenduService.lstArticlesVendus().get(j);
//				System.out.println(articleVenduService.trouvePseudoParNo(a.getNoArticle()));
                a.setUtilisateur(u);
//				System.out.println(articleVenduService.trouveCategorieParNo(a.getNoArticle()));
                for (Categorie c : lstCategories) {
                    if (articleVenduService.trouveCategorieParNo((Integer) a.getNoArticle()).equals(c.getLibelle())
                            && articleVenduService.trouvePseudoParNo((Integer) a.getNoArticle()).equals(u.getPseudo())) {
                        a.setCategorie(c);
                        System.out.println(a);
//                        System.out.println(c);
                        model.addAttribute("articleVendu", a);
                    }
                }
            }
        }

		List<ArticleVendu> articles = articleVenduService.trouveParCat("Informatique");
// Affiche tous les articles
//		for (ArticleVendu article : articles) {
//			System.out.println(article);
//		}
		return "index";
	}

	@GetMapping("/login")
	public String connexion() {
		return "login";
	}

	@GetMapping("/accueil_connecte")
	public String connecter(Model model) {
//		List<Categorie> lstCategories = articleVenduService.lstCategories();
//		model.addAttribute("categories", lstCategories);
		return "listeEncheresConnecte";
	}

	@GetMapping("/creer_compte")
	public String creerCompte(Model model) {
		model.addAttribute("utilisateur", new Utilisateur());
		return "creerCompte";
	}

	@GetMapping("/profil")
	public String profil(Model model) {

		List<Utilisateur> lstUtilisateurs = utilisateurService.findAll();
		for (int index = 0; index < lstUtilisateurs.size(); index++) {
			Utilisateur u = utilisateurService.findAll().get(index);
			if ((u.getPseudo()).equals("AA")) {
				System.out.println(u.getPseudo());
				model.addAttribute("utilisateur", u);
			}
		}
		return "profilUtilisateur";
	}

	@GetMapping("/mon_profil")
	public String monprofil(Model model, Principal principal) {
		Utilisateur u = utilisateurService.findByPseudo(principal.getName());
		System.out.println(u);
		model.addAttribute("utilisateur", u);
		return "monProfil";
	}

	@GetMapping("/modifier_profil")
	public String modifierProfil(Model model, Principal principal) {
		Utilisateur u = utilisateurService.findByPseudo(principal.getName());
		model.addAttribute("utilisateur", u);
		return "modifProfil";
	}

	@PostMapping("/ajout")
	String ajout(@ModelAttribute("utilisateur") @Valid Utilisateur utilisateur, BindingResult br, Model model,
			String confirmMdp) {
		String retour = "redirect:/";
		if (br.hasErrors()) {
			retour = "creerCompte";
			model.addAttribute("utilisateur", utilisateur);
		} else {
			try {
				utilisateurService.ajouter(utilisateur, confirmMdp);
			} catch (MetierException e) {
				model.addAttribute("erreur", e.getMessage());
				retour = "creerCompte";
				model.addAttribute("utilisateur", utilisateur);
			}
		}
		return retour;
	}

	@PostMapping("/enregistrer_profil")
	public String enregistrerprofil(@ModelAttribute("utilisateur") @Valid Utilisateur u, BindingResult br,
			String nouvMotDePasse, String confirmMdp, Model model, Principal principal) {

		Utilisateur utilisateur = utilisateurService.findByPseudo(principal.getName());
		u.setNoUtilisateur(utilisateur.getNoUtilisateur());

		String retour = "redirect:/logout";

		if (br.hasErrors()) {
			retour = "modifProfil";
			model.addAttribute("utilisateur", u);
		} else {
			try {
				utilisateurService.modifier(u, principal, nouvMotDePasse, confirmMdp);
			} catch (MetierException e) {
				model.addAttribute("erreur", e.getMessage());
				retour = "modifProfil";
				model.addAttribute("utilisateur", u);
			}
		}
		return retour;
	}

	@GetMapping("/supprimer")
	public String supprimerProfil(Principal principal) {
		utilisateurService.supprimer(principal.getName());
		return "redirect:/logout";
	}

}
