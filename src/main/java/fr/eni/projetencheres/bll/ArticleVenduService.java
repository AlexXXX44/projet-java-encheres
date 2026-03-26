package fr.eni.projetencheres.bll;

import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Categorie;
import fr.eni.projetencheres.dal.ArticleVenduDAO;
import fr.eni.projetencheres.dal.ArticleVenduRepository;
import fr.eni.projetencheres.dal.CategorieRepository;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleVenduService {

    private final ArticleVenduDAO articleVenduDAO;
    private final CategorieRepository categorieRepository;
    private final ArticleVenduRepository repository;

    public ArticleVenduService(ArticleVenduRepository repository,
                                CategorieRepository categorieRepository,
                           ArticleVenduDAO articleVenduDAO) {
        this.repository = repository;
        this.categorieRepository = categorieRepository;
        this.articleVenduDAO = articleVenduDAO;
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

    public ArticleVendu findById(int id) {
         return repository.findById(id).orElse(null);
    }

    public List<ArticleVendu> lstArticles() {
        return repository.findAll();
    }

    public List<Categorie> findAllCategories() {
        return categorieRepository.findAll();
    }

    public ArticleVendu trouveParNo(int noArticle) {
        return articleVenduDAO.findById(noArticle);
    }

    public void supprimerArticleVendu(ArticleVendu article) {
        repository.delete(article);
    }

    public void modifierArticleVendu(ArticleVendu a) {
        repository.save(a);
    }

    public List<ArticleVendu> trouveParCat(Integer noCategorie) {
        return repository.findByNoCategorie_NoCategorie(noCategorie,
             PageRequest.of(0, 10))
            .getContent();
    }

    public Object trouvePseudoParNo(@NonNull Integer noArticle) {
        return repository.findById(noArticle)
                .map(ArticleVendu::getVendeur)
                .map(utilisateur -> utilisateur.getPseudo())
                .orElse(null);
    }

    public Object trouveCategorieParNo(@NonNull Integer noArticle) {
        return repository.findById(noArticle)
                .map(ArticleVendu::getCategorie)
                .map(Categorie::getLibelle)
                .orElse(null);
    }
}