package fr.eni.projetencheres.bll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Enchere;
import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dal.UtilisateurRepository;
import fr.eni.projetencheres.dal.EnchereRepository;

@Service
public class EnchereService {
    
    private EnchereRepository enchereRepository;
    private UtilisateurRepository utilisateurRepository;

    public EnchereService(UtilisateurRepository utilisateurRepository,
        EnchereRepository enchereRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.enchereRepository = enchereRepository;
    }
 
    @Transactional
    public void faireEnchere(Utilisateur utilisateur, ArticleVendu article, int montant) {

        if (utilisateur == null) {
            throw new IllegalArgumentException("Utilisateur ne doit pas être null");
        }

        if (article == null) {
            throw new IllegalArgumentException("Article ne doit pas être null");
        }

        if(article.getDateFinEncheres().isBefore(LocalDateTime.now().toLocalDate())) {
            throw new RuntimeException("Enchères terminées");
        }

        // 1. Vérifier montant minimum
        int prixActuel = getPrixActuel(article);

        if (montant <= prixActuel) {
            throw new RuntimeException("Montant trop bas");
        }

        // 2. Vérifier crédit
        if (utilisateur.getCredit() < montant) {
            throw new RuntimeException("Crédit insuffisant");
        }
            
        // 🔎 3. Récupérer la meilleure enchère actuelle
        Enchere meilleure = (Enchere) enchereRepository
                .findTopByArticleOrderByMontantEnchereDesc(article);

        // 4. Rembourser ancien enchérisseur
        if (meilleure != null) {
            Utilisateur ancien = meilleure.getUtilisateur();

            ancien.setCredit(ancien.getCredit() + meilleure.getMontantEnchere());
            utilisateurRepository.save(ancien);
        }

        // 5. Déduire crédit nouveau
        utilisateur.setCredit(utilisateur.getCredit() - montant);
        utilisateurRepository.save(utilisateur);

        // 6. Créer enchère
        Enchere enchere = new Enchere();
        enchere.setArticleVendu(article);
        enchere.setUtilisateur(utilisateur);
        enchere.setMontantEnchere(montant);
        enchere.setDateEnchere(LocalDate.now());

        enchereRepository.save(enchere);

        if (meilleure != null && meilleure.getUtilisateur().getNoUtilisateur() == utilisateur.getNoUtilisateur()) {
            throw new RuntimeException("Vous êtes déjà le meilleur enchérisseur");
        }

        if (meilleure != null && meilleure.getUtilisateur().getNoUtilisateur() == utilisateur.getNoUtilisateur()) {
            throw new RuntimeException("Vous êtes déjà le meilleur enchérisseur");
        }
}

    public int getPrixActuel(ArticleVendu article) {
        Enchere meilleure = (Enchere) enchereRepository
            .findTopByArticleOrderByMontantEnchereDesc(article);

        return (meilleure != null) 
         ? meilleure.getMontantEnchere()
         : article.getMiseAPrix();
    }
}