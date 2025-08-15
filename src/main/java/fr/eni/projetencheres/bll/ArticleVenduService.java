package fr.eni.projetencheres.bll;

import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Categorie;
import fr.eni.projetencheres.dal.ArticleVenduDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleVenduService {

    @Autowired
    private final ArticleVenduDAO articleVenduDAO;

    public ArticleVenduService(ArticleVenduDAO articleVenduDAO) {
        this.articleVenduDAO = articleVenduDAO;
    }

    public List<ArticleVendu> lstArticlesVendus() {
        return articleVenduDAO.lstArticles();
    }

    public List<Categorie> findAllCategories() {
        return articleVenduDAO.findAllCategories();
    }

    public List<Categorie> lstCategories() {
        return articleVenduDAO.findAllCategories();
    }

    public String trouveCategorieParNo(int noArticle) {
        return articleVenduDAO.trouveCategorieParNo(noArticle);
    }

    public ArticleVendu findById(int id) {
        return articleVenduDAO.findById(id);
    }

    public ArticleVendu trouveParNo(int noArticle) {
        return articleVenduDAO.findById(noArticle);
    }

    public List<ArticleVendu> trouveParCat(String libelle) {
        return articleVenduDAO.findByCat(libelle);
    }

    public void supprimerArticleVendu(ArticleVendu nomArticle) {
        articleVenduDAO.delete(nomArticle);
    }

    public void modifierArticleVendu(ArticleVendu a) {
        articleVenduDAO.modifier(a);
    }

    public String trouvePseudoParNo(int noArticle) {
        return articleVenduDAO.trouvePseudoParNo(noArticle);
    }

    public List<ArticleVendu> getArticles(int page, int size, String sortBy, String sortDir) {
        return List.of();
    }
}