package fr.eni.projetencheres.bll;

import fr.eni.projetencheres.bo.Role;
import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dal.RoleRepository;
import fr.eni.projetencheres.dal.UtilisateurDao;
import fr.eni.projetencheres.dal.UtilisateurDto;
import fr.eni.projetencheres.dal.UtilisateurRepository;
import fr.eni.projetencheres.exception.MetierException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private UtilisateurDao utilisateurDAO;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurService(UtilisateurRepository utilisateurRepository,
                              RoleRepository roleRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Transactional
    public Utilisateur registerNewUser(UtilisateurDto dto) {
        // Sauvegarde utilisateur
        Utilisateur u = new Utilisateur();
        u.setPseudo(dto.getPseudo());
        u.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        u.setEmail(dto.getEmail());
        u.setCredit(100);
        utilisateurRepository.save(u);

        // Sauvegarde du rôle
        Role r = new Role();
        r.setPseudo(dto.getPseudo());
        r.setRole("ROLE_USER");
        roleRepository.save(r);

        return u;
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
	private void validateMdp(Utilisateur u, Principal principal) throws MetierException {

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

	public List<Utilisateur> findAll() {
		return utilisateurDAO.findAll();
	}

	public Utilisateur findByPseudo(String pseudo) {
		return utilisateurDAO.findByPseudo(pseudo);
	}

	@Transactional
	public void supprimer(String pseudo) {
		utilisateurDAO.supprimerUtilisateur(pseudo);
		utilisateurDAO.supprimerRole(pseudo);
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
	public Utilisateur ajouter(Utilisateur u, String confirmMdp) throws MetierException {
		validateConfirmMdp(confirmMdp);
		validatePseudo(u);
		validateEmail(u);
		u.setCredit(1000);
		u.setAdministrateur(false);
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		String mpchiffrer = encoder.encode(u.getMotDePasse());
		u.setMotDePasse(mpchiffrer);
		utilisateurDAO.add(u);
        return u;
    }
}
