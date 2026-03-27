package fr.eni.projetencheres.bll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dal.UtilisateurRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepo;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        if (email == null || email.trim().isEmpty()) {
            throw new UsernameNotFoundException("Identifiant manquant");
        }

        Utilisateur utilisateur = utilisateurRepo.findByEmail(email.trim());

        if (utilisateur == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé : " + email);
        }

        return User.builder()
                .username(utilisateur.getEmail()) // ✔ cohérent
                .password(utilisateur.getMotDePasse()) // ✔ mot de passe DB
                .roles(utilisateur.getAdministrateur() ? "ADMIN" : "USER")
                .build();
    }
}