package fr.eni.projetencheres.ihm;

import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dal.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UtilisateurController {

    @Autowired
    private UtilisateurRepository utilisateurRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "inscription";
    }

    @PostMapping("/register")
    public String register(@RequestParam String nom,
                           @RequestParam String email,
                           @RequestParam String motDePasse) {
        Utilisateur u = new Utilisateur();
        u.setNom(nom);
        u.setEmail(email);
        u.setMotDePasse(passwordEncoder.encode(motDePasse));
        u.setCredit(100); // bonus initial
        String tel = u.getTelephone().replaceAll("\\D", "");
        u.setTelephone(tel);
        utilisateurRepo.save(u);
        return "redirect:/login";
    }

    @GetMapping("/connexion")
    public String loginPage() {
        return "login";
    }
}
