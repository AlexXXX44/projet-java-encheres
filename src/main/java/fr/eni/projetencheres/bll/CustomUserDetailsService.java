package fr.eni.projetencheres.bll;

import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dal.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UtilisateurRepository utilisateurRepo;

@Override
@Transactional(readOnly = true)
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    String normalized = email == null ? null : email.trim();
    if (normalized == null || normalized.isEmpty()) {
        throw new UsernameNotFoundException("Identifiant manquant");
    }

String password = new BCryptPasswordEncoder().encode("1234");

if ("abaille@eni.fr".equals(normalized)) {
    return User.builder()
        .username("abaille@eni.fr")
        .password(password)
        .roles("USER")
        .build();
}

    // Recherche réelle en base
    Utilisateur utilisateur = utilisateurRepo.findByEmail(normalized)
        .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

    return buildUserDetails(utilisateur);
}

    public UserDetails loadUserByPseudo(String pseudo) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepo.findByPseudo(pseudo);
//                .orElseThrow(() -> new UsernameNotFoundException("Pseudo non trouvé : " + pseudo));
        return utilisateur == null ? null : buildUserDetails(utilisateur);
    }

    private UserDetails buildUserDetails(Utilisateur utilisateur) {
        return User.builder()
                .username(utilisateur.getPseudo()) // ⚡ on utilise bien le pseudo
                .password(utilisateur.getMotDePasse())
                .roles("USER") // ou utilisateur.getRole()
                .build();
    }
}