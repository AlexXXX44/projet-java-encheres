package fr.eni.projetencheres.ihm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import jakarta.validation.Valid;

import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dal.UtilisateurRepository;

@Controller
@RequestMapping("/utilisateur")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurSService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "creerUser"; // ⚠️ cohérent avec ton template
    }

    @PostMapping("/ajout")
    public String creerUtilisateur(@Valid Utilisateur utilisateur,
                                   BindingResult result,
                                   Model model) {

        if (result.hasErrors()) {
            return "creerUser";
        }

    // Encodage mot de passe
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));

    // Bonus initial
        utilisateur.setCredit(100);

        utilisateurService.save(utilisateur);

        return "redirect:/login";
    }

    @GetMapping("/connexion")
    public String loginPage() {
        return "login";
    }
}
