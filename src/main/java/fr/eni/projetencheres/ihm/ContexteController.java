package fr.eni.projetencheres.ihm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import fr.eni.projetencheres.bll.contexte.ContexteService;
import fr.eni.projetencheres.bo.Utilisateur;

@Controller
public class ContexteController {

	@Autowired
	private ContexteService service;

	public String connexion(@ModelAttribute("utilisateurEnSession") Utilisateur utilisateurEnSession, String email) {
		Utilisateur aCharger = service.charger(email);
		if (aCharger != null) {
			utilisateurEnSession.setNoUtilisateur(aCharger.getNoUtilisateur());
			utilisateurEnSession.setNom(aCharger.getNom());
			utilisateurEnSession.setPrenom(aCharger.getPrenom());
			utilisateurEnSession.setPseudo(aCharger.getPseudo());
			utilisateurEnSession.setAdministrateur(aCharger.isAdmin());

		} else {
			utilisateurEnSession.setNoUtilisateur(0);
			utilisateurEnSession.setNom(null);
			utilisateurEnSession.setPrenom(null);
			utilisateurEnSession.setPseudo(null);
			utilisateurEnSession.setAdministrateur(false);
		}
		return null;
	}
}