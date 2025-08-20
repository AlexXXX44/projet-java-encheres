package fr.eni.projetencheres.bll;

import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Categorie;
import fr.eni.projetencheres.dal.ArticleVenduDAO;
import fr.eni.projetencheres.dal.ArticleVenduRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleVenduService {

    private final ArticleVenduRepository repository;
    @Autowired
    private ArticleVenduDAO articleVenduDAO;

    public ArticleVenduService(ArticleVenduRepository repository) {
        this.repository = repository;
    }

    // Si vous gardez cette méthode, faites-la vraiment remonter des données
    public List<ArticleVendu> getArticles(int page, int size, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(Math.max(0, page - 1), Math.max(1, size));
        return repository.findAll(pageable).getContent();
    }

    public List<ArticleVendu> search(String keyword, Integer noCategorie, int page, int size) {
        String kw = keyword == null ? "" : keyword.trim();
        Pageable pageable = PageRequest.of(Math.max(0, page - 1), Math.max(1, size));

        if (noCategorie != null) {
            return repository
                    .findBynoCategorieAndNomArticleContainingIgnoreCase(noCategorie, kw, pageable)
                    .getContent();
        }
        return repository
                .findByNomArticleContainingIgnoreCase(kw, pageable)
                .getContent();
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

    public List<ArticleVendu> lstArticlesVendus() {
        return articleVenduDAO.lstArticles();
    }
}