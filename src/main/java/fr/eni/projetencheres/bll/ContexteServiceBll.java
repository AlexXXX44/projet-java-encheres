package fr.eni.projetencheres.bll;

import java.util.List;

import fr.eni.projetencheres.bll.contexte.ContexteService;
import org.springframework.stereotype.Service;

import fr.eni.projetencheres.bo.Utilisateur;

@Service
public class ContexteServiceBll implements ContexteService {

	private static List<Utilisateur> lstUtilisateurs;

	public Utilisateur charger(String email) {
		return lstUtilisateurs.stream().filter(utilisateur -> utilisateur.getPseudo().equals(email)).findAny()
				.orElse(null);

	}
}
