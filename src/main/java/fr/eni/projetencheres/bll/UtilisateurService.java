package fr.eni.projetencheres.bll;

import fr.eni.projetencheres.bo.Role;
import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dal.RoleRepository;
import fr.eni.projetencheres.dal.UtilisateurDao;
import fr.eni.projetencheres.dal.UtilisateurDto;
import fr.eni.projetencheres.dal.UtilisateurRepository;
import fr.eni.projetencheres.exception.MetierException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

	@Autowired
    private UtilisateurRepository utilisateurRepository;
	@Autowired
    private UtilisateurDao utilisateurDAO;
	@Autowired
    private RoleRepository roleRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;

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

		public UtilisateurService(UtilisateurRepository utilisateurRepository, UtilisateurDao utilisateurDAO,
                                  RoleRepository roleRepository,
                                  PasswordEncoder passwordEncoder) {
			this.utilisateurRepository = utilisateurRepository;
            this.utilisateurDAO = utilisateurDAO;
            this.roleRepository = roleRepository;
			this.passwordEncoder = passwordEncoder;
		}

		public Utilisateur ajouter(UtilisateurDto dto, String confirmMdp) throws MetierException {
			// 1️⃣ Vérification du mot de passe et confirmation
			if (!dto.getMotDePasse().equals(confirmMdp)) {
				throw new MetierException("Les mots de passe ne correspondent pas");
			}

			// 2️⃣ Vérification que le pseudo n’existe pas déjà
			if (utilisateurRepository.existsByPseudo((dto.getPseudo()))) {
				throw new MetierException("Ce pseudo est déjà utilisé");
			}

			// 3️⃣ Création de l’utilisateur
			Utilisateur utilisateur = new Utilisateur();
			utilisateur.setPseudo(dto.getPseudo());
			utilisateur.setEmail(dto.getEmail());
			utilisateur.setNom(dto.getNom());
			utilisateur.setPrenom(dto.getPrenom());
			utilisateur.setTelephone(dto.getTelephone());
			utilisateur.setRue(dto.getRue());
			utilisateur.setCodePostal(dto.getCodePostal());
			utilisateur.setVille(dto.getVille());

			// 4️⃣ Encodage du mot de passe
			utilisateur.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));

			// 5️⃣ Sauvegarde de l’utilisateur
			Utilisateur saved = utilisateurRepository.save(utilisateur);

			// 6️⃣ Ajout du rôle par défaut (ROLE_USER)
			roleRepository.save(new Role(saved.getPseudo(), "ROLE_USER"));

			return saved;
		}

		public Utilisateur findByPseudoOrThrow(String pseudo) {
			Utilisateur user = utilisateurRepository.findByPseudo(pseudo);
			if (user == null) {
				throw new RuntimeException("Utilisateur non trouvé : " + pseudo);
			}
			return user;
		}

        public Utilisateur findByEmailOrThrow(String email) {
			Utilisateur user = utilisateurRepository.findByEmail(email);
			if (user == null) {
				throw new RuntimeException("Utilisateur non trouvé : " + email);
			}
			return user;
        }

		public List<Utilisateur> findAll() {
			return utilisateurRepository.findAll();
		}

        public Utilisateur findByEmail(String name) {
			return utilisateurRepository.findByEmail(name);
        }

		//	@Transactional
//	public Utilisateur ajouter(@Valid UtilisateurDto u, String confirmMdp) throws MetierException {
//		validateConfirmMdp(confirmMdp);
//		validatePseudo(u);
//		validateEmail(u);
//		u.setCredit(1000);
//		u.setAdministrateur(false);
//		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//		String mpchiffrer = encoder.encode(u.getMotDePasse());
//		u.setMotDePasse(mpchiffrer);
//		utilisateurDAO.add(u);
//        return u;
//    }
}
