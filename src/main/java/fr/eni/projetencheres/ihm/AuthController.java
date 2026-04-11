package fr.eni.projetencheres.ihm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.eni.projetencheres.bll.UtilisateurService;
import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dal.RoleRepository;
import fr.eni.projetencheres.dal.UtilisateurDto;
import fr.eni.projetencheres.dal.UtilisateurRepository;

@Controller
public class AuthController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private final UtilisateurService utilisateurService;

    public AuthController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "inscription";
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("utilisateur", new UtilisateurDto());
        return "connexion";
    }

    @PostMapping("/register")
    public String registerUser(
        @ModelAttribute Utilisateur user,
        RedirectAttributes redirectAttributes
    ){

        //hash du mot de passe
        user.setMotDePasse(passwordEncoder.encode(user.getMotDePasse()));
        user.setCredit(100); // crédit de départ
        user.setAdministrateur(false); // pas admin par défaut
        //user.setRole(roleRepository.findById(2).orElseThrow()); // rôle USER par défaut
        utilisateurRepository.save(user);

        redirectAttributes.addFlashAttribute("success", "Inscription réussie ! Vous pouvez maintenant vous connecter.");
    
        return "redirect:/connexion";
    }
}
//    @PostMapping("/register")
//    public String registerUser(
//        @ModelAttribute("utilisateur") @Valid UtilisateurDto dto,
//                           BindingResult br,
//                           Model model,
//                           String confirmMdp) throws MetierException {

//        if (br.hasErrors()) {
//            return "inscription";
//        }

        // 1️⃣ Persister l’utilisateur via le service
//        Utilisateur u = utilisateurService.ajouter(dto, confirmMdp);

        // 2️⃣ Connexion auto après inscription
//        UserDetails user = org.springframework.security.core.userdetails.User
//                .withUsername(u.getPseudo())
//                .password(u.getMotDePasse()) // déjà encodé par le service
//                .roles("USER")
//                .build();

//        UsernamePasswordAuthenticationToken auth =
//                new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());

//        SecurityContextHolder.getContext().setAuthentication(auth);

        // 3️⃣ Redirection vers page d’accueil
//        return "redirect:/";
//    }
//}
