package fr.eni.projetencheres.bll;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Enchere;
import fr.eni.projetencheres.dal.ArticleVenduRepository;
import fr.eni.projetencheres.dal.EnchereRepository;

@Service
public class EnchereScheduler {

    @Autowired
    private ArticleVenduRepository articleRepo;

    @Autowired
    private EnchereRepository enchereRepo;

    @Scheduled(fixedRate = 60000) // toutes les 60 sec
    public void verifierFinEncheres() {

        List<ArticleVendu> articles = articleRepo.findAll();

        for (ArticleVendu article : articles) {

            if (article.getDateFinEncheres().isBefore(LocalDate.now())
                && "EN_COURS".equals(article.getEtatVente())) {

                traiterFinEnchere(article);
            }
        }
    }

    private void traiterFinEnchere(ArticleVendu article) {

        Enchere meilleure = enchereRepo
            .findTopByArticleOrderByMontantEnchereDesc(article);

        if (meilleure != null) {
            article.setEtatVente("VENDUE");

            System.out.println("🏆 Gagnant : "
                + meilleure.getUtilisateur().getPseudo());
        } else {
            article.setEtatVente("TERMINEE");
        }

        articleRepo.save(article);
    }
}