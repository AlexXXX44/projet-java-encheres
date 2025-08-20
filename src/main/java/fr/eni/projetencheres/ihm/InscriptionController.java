package fr.eni.projetencheres.ihm;

import fr.eni.projetencheres.bll.UtilisateurService;
import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dal.UtilisateurDto;
import fr.eni.projetencheres.exception.MetierException;
import jakarta.validation.Valid;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class InscriptionController {

    private final UtilisateurService utilisateurService;

    public InscriptionController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/inscription")
    public String showForm(Model model) {
        model.addAttribute("utilisateur", new UtilisateurDto());
        return "inscription";
    }

    @PostMapping("/inscription")
    public String register(@ModelAttribute("utilisateur") @Valid UtilisateurDto dto,
                           BindingResult br,
                           Model model,
                           String confirmMdp) throws MetierException {

        if (br.hasErrors()) {
            return "inscription";
        }

        // 1️⃣ Persister l’utilisateur via le service
        Utilisateur u = utilisateurService.ajouter(dto, confirmMdp);

        // 2️⃣ Connexion auto après inscription
        UserDetails user = org.springframework.security.core.userdetails.User
                .withUsername(u.getPseudo())
                .password(u.getMotDePasse()) // déjà encodé par le service
                .roles("USER")
                .build();

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);

        // 3️⃣ Redirection vers page d’accueil
        return "redirect:/";
    }
}
