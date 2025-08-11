package fr.eni.projetencheres.bll;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dal.UtilisateurDao;
import fr.eni.projetencheres.exception.MetierException;

@Service
public class UtilisateurService {

	@Autowired
	public UtilisateurDao utilisateurDAO;

	public Utilisateur findByPseudo(String pseudo) {
		return utilisateurDAO.findByPseudo(pseudo);
	}

	public List<Utilisateur> findAll() {
		return utilisateurDAO.findAll();
	}
	@Transactional
	public void ajouter(Utilisateur u, String confirmMdp) throws MetierException {
		validateConfirmMdp(confirmMdp);
		validatePseudo(u);
		validateEmail(u);
		u.setCredit(1000);
		u.setAdministrateur(false);
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		String mpchiffrer = encoder.encode(u.getMotDePasse());
		u.setMotDePasse(mpchiffrer);
		utilisateurDAO.add(u);
	}
	@Transactional
	public void modifier(Utilisateur u, Principal principal, String nouvMotDePasse, String confirmMdp) throws MetierException{

		Utilisateur utilisateur = utilisateurDAO.findByPseudo(principal.getName());

		if(!(u.getMotDePasse().isBlank())){
			validateMdp(u, principal);
		}

		if (u.getMotDePasse().equals(nouvMotDePasse) || nouvMotDePasse.isBlank()){
			System.out.println(" mp idem");
			u.setMotDePasse(u.getMotDePasse());
		}else {
			System.out.println(" mp différent");
			u.setMotDePasse(nouvMotDePasse);
		}

		validateConfirmMdp(confirmMdp);

		if(!(utilisateur.getPseudo().equals(u.getPseudo()))){
		validatePseudo(u);
		}
		if(!(utilisateur.getEmail().equals(u.getEmail()))){
			validateEmail(u);
		}

		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		System.out.println(u.getMotDePasse());
		String mpchiffrer = encoder.encode(u.getMotDePasse());
		u.setMotDePasse(mpchiffrer);
		utilisateurDAO.modifierUtilisateur(u);
		utilisateurDAO.modifierRole(u);
	}

	@Transactional
	public void supprimer(String pseudo) {
		utilisateurDAO.supprimerUtilisateur(pseudo);
		utilisateurDAO.supprimerRole(pseudo);
	}


	private void validatePseudo(Utilisateur u) throws MetierException {
		if (utilisateurDAO.validateUtilisateurPseudo(u.getPseudo()) != 0) {
		throw new MetierException("Pseudo existant") ;
		}
	}

	private void validateEmail(Utilisateur u) throws MetierException {
		if (utilisateurDAO.validateUtilisateurEmail(u.getEmail()) != 0) {
		throw new MetierException("Email existant") ;
		}
	}
	private void validateMdp(Utilisateur u,Principal principal) throws MetierException {

		Utilisateur utilisateur = utilisateurDAO.findByPseudo(principal.getName());
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		System.out.println(u.getMotDePasse());
		System.out.println(encoder.matches(u.getMotDePasse(), utilisateur.getMotDePasse()));

		if (encoder.matches(u.getMotDePasse(), utilisateur.getMotDePasse()) == false) {
			throw new MetierException("Le mot de passe actuel n'est pas bon") ;
		}
	}
	private void validateConfirmMdp(String confirmMdp) throws MetierException {
		if (confirmMdp.isBlank()) {
			throw new MetierException("Confirmation de mot de passe obligatoire") ;
		}
	}















}