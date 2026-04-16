package fr.eni.projetencheres.ihm;

import fr.eni.projetencheres.bo.Enchere;
import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dal.EnchereRepository;
import fr.eni.projetencheres.dal.UtilisateurRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/profil")
public class ProfilController {
    @Autowired
    private EnchereRepository enchereRepo;
    @Autowired
    private UtilisateurRepository utilisateurRepo;
    
    @GetMapping("/edit")
    public String modifierProfil(Principal principal, Model model) {

        Utilisateur utilisateur = utilisateurRepo.findByEmail(principal.getName());

        model.addAttribute("utilisateur", utilisateur);

        return "modifierProfil";
    }

    @PostMapping("/register")
    public String enregistrerProfil(
   
            @ModelAttribute Utilisateur utilisateurForm,
            Principal principal,
            RedirectAttributes redirectAttributes) {
    
        Utilisateur utilisateur = utilisateurRepo.findByEmail(principal.getName());
    
        // 🔥 on met à jour champs autorisés
        utilisateur.setNom(utilisateurForm.getNom());
        utilisateur.setPrenom(utilisateurForm.getPrenom());
        utilisateur.setTelephone(utilisateurForm.getTelephone());
        utilisateur.setRue(utilisateurForm.getRue());
        utilisateur.setVille(utilisateurForm.getVille());
        utilisateur.setCodePostal(utilisateurForm.getCodePostal());
    
        utilisateurRepo.save(utilisateur);
    
        redirectAttributes.addFlashAttribute("message", "Profil mis à jour !");
    
        return "redirect:/profil";
    }

    @GetMapping("/utilisateur")
    public String afficherProfil(Principal principal, Model model) {    
        Utilisateur utilisateur = utilisateurRepo.findByEmail(principal.getName());
        model.addAttribute("utilisateur", utilisateur);
        return "profilUtilisateur";
    }

    @GetMapping("/encheres")
    public String mesEncheres(@AuthenticationPrincipal Utilisateur utilisateur, Model model) {

        List<Enchere> mesEncheres =
                enchereRepo.findByUtilisateur_NoUtilisateur(utilisateur.getNoUtilisateur());

        model.addAttribute("mesEncheres", mesEncheres);

        return "listeEncheresConnecte"; // 🔥 ICI
    }

    @GetMapping("/encheres/gagnees")
    public String mesEncheresGagnees(@AuthenticationPrincipal Utilisateur utilisateur, Model model) {
        List<Enchere> mesEncheres = enchereRepo
                .findByUtilisateur_NoUtilisateur(utilisateur.getNoUtilisateur());

        Map<Integer, Enchere> meilleuresEncheres = new HashMap<>();

        for (Enchere e : mesEncheres) {
            Integer noArticle = e.getArticle().getNoArticle();

            meilleuresEncheres.merge(noArticle, e,
                    (existante, nouvelle) ->
                            nouvelle.getMontantEnchere() > existante.getMontantEnchere()
                                    ? nouvelle
                                    : existante);
        }

        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("mesEncheres", mesEncheres);
        model.addAttribute("mesEncheresGagnees", meilleuresEncheres.values());
        return "monProfil";
    }
}