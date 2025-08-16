package fr.eni.projetencheres.ihm;

import fr.eni.projetencheres.dal.UtilisateurDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class InscriptionController {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public InscriptionController(UserDetailsManager userDetailsManager){
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @GetMapping("/inscription")
    public String showForm(Model model) {
        model.addAttribute("utilisateur", new UtilisateurDto());
        return "inscription"; // Thymeleaf view
    }

    @PostMapping("/inscription")
    public String register(@ModelAttribute("utilisateur") UtilisateurDto dto, HttpServletRequest request) {
        UserDetails user = User.withUsername(dto.getPseudo()) // 👈 pas ton entity, mais org.springframework.security.core.userdetails.User
                .password(passwordEncoder.encode(dto.getMotDePasse()))
                .roles("USER")
                .build();

        userDetailsManager.createUser(user);

        // 🔑 Connexion automatique
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/"; // redirige vers la page d'accueil
    }
}

