package fr.eni.projetencheres.ihm;

import fr.eni.projetencheres.bo.Enchere;
import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dal.EnchereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/profil")
public class ProfilController {

    @Autowired
    private EnchereRepository enchereRepo;

    @GetMapping
    public String monProfil(@AuthenticationPrincipal Utilisateur utilisateur, Model model) {
        // Toutes les enchères faites par cet utilisateur
        List<Enchere> mesEncheres = enchereRepo.findByUtilisateur_NoUtilisateur(utilisateur.getNoUtilisateur());

        // Identifier celles qu'il a gagnées (meilleure enchère par article)
        Map<Integer, Enchere> meilleuresEncheres = new HashMap<>();
        for (Enchere e : mesEncheres) {
            Integer noArticle = e.getArticle().getNoArticle();
            meilleuresEncheres.merge(noArticle, e, (existante, nouvelle) ->
                    nouvelle.getMontantEnchere() > existante.getMontantEnchere() ? nouvelle : existante);
        }

        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("mesEncheres", mesEncheres);
        model.addAttribute("mesEncheresGagnees", meilleuresEncheres.values());

        return "profil";
    }
}