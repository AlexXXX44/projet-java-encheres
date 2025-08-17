package fr.eni.projetencheres.bll;

import fr.eni.projetencheres.bo.Role;
import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dal.RoleRepository;
import fr.eni.projetencheres.dal.UtilisateurRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;

    public CustomUserDetailsService(UtilisateurRepository utilisateurRepository,
                                    RoleRepository roleRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur u = utilisateurRepository.findByPseudo(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        List<Role> roles = roleRepository.findByPseudo(username);

        return org.springframework.security.core.userdetails.User.withUsername(u.getPseudo())
                .password(u.getMotDePasse()) // déjà encodé
                .authorities(roles.stream().map(Role::getRole).toArray(String[]::new))
                .build();
    }
}
