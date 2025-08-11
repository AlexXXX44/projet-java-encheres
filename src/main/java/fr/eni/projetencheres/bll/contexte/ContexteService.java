package fr.eni.projetencheres.bll.contexte;

import fr.eni.projetencheres.bo.Utilisateur;

public interface ContexteService {
	Utilisateur charger(String email);
}
