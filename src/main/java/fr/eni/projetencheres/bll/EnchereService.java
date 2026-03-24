package fr.eni.projetencheres.bll;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Enchere;
import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dal.UtilisateurRepository;
import fr.eni.projetencheres.dal.EnchereRepository;

@Service
@Transactional
public class EnchereService {
    
    private EnchereRepository enchereRepository;
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    public EnchereService(UtilisateurRepository utilisateurRepository,
        EnchereRepository enchereRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.enchereRepository = enchereRepository;
    }
    
    public void faireEnchere(Utilisateur utilisateur, ArticleVendu article, int montant) {

        System.out.println(this.enchereRepository.findTopByArticleOrderByMontantEnchereDesc(article));
    // 🔎 1. Récupérer la meilleure enchère actuelle
    Optional<Enchere> meilleure = (Optional<Enchere>) enchereRepository
            .findTopByArticleOrderByMontantEnchereDesc(article);

    int prixActuel = meilleure.map(Enchere::getMontantEnchere)
                              .orElse(article.getMiseAPrix());

    // ❌ montant insuffisant
    if (montant <= prixActuel) {
        throw new RuntimeException("Montant insuffisant !");
    }

    utilisateur = utilisateurRepository.findByEmail("abaille@eni.fr");
    if (utilisateur == null) {
        throw new RuntimeException("Utilisateur introuvable !");
    }
    int credit = utilisateur.getCredit();
    // ❌ pas assez de crédits
    if (utilisateur.getCredit() < montant) {
        throw new RuntimeException("Crédits insuffisants !");
    }

    // 🔁 rembourser ancien meilleur enchérisseur
    if (meilleure.isPresent()) {
        Enchere ancienne = meilleure.get();
        Utilisateur ancienUser = ancienne.getUtilisateur();
        ancienUser.setCredit(ancienUser.getCredit() + ancienne.getMontantEnchere());
    }

    // 💸 débiter le nouvel utilisateur
    utilisateur.setCredit(utilisateur.getCredit() - montant);

    // 💾 enregistrer enchère
    Enchere enchere = new Enchere();
    enchere.setArticleVendu(article);
    enchere.setUtilisateur(utilisateur);
    enchere.setMontantEnchere(montant);
    enchere.setDateEnchere(LocalDate.now());

    enchereRepository.save(enchere);
    }
}