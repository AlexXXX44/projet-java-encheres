package fr.eni.projetencheres.ihm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import jakarta.validation.Valid;

import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.bll.UtilisateurService;
import fr.eni.projetencheres.dal.UtilisateurDto;
import fr.eni.projetencheres.exception.MetierException;

@Controller
@RequestMapping("/utilisateur")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("utilisateur", new UtilisateurDto());
        return "creerUser";
    }

    @PostMapping("/ajout")
    public String creerUtilisateur(@ModelAttribute UtilisateurDto dto,
                                @RequestParam String confirmMdp,
                                Model model) {

        try {
            utilisateurService.ajouter(dto, confirmMdp);
            return "redirect:/login";

        } catch (MetierException e) {
            model.addAttribute("erreur", e.getMessage());
            model.addAttribute("utilisateur", dto);
            return "creerUser";
        }
    }

    @GetMapping("/connexion")
    public String loginPage() {
        return "login";
    }
}