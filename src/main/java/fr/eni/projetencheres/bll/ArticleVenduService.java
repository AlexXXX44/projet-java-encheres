package fr.eni.projetencheres.bll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Categorie;
import fr.eni.projetencheres.dal.ArticleVenduDAO;

@Service
public class ArticleVenduService {

    @Autowired
    private ArticleVenduDAO articleVenduDAO;

	public String trouveCategorieParNo(int noArticle) {
        return articleVenduDAO.trouveCategorieParNo(noArticle);
    }

    public List<ArticleVendu> lstArticlesVendus() {
        return articleVenduDAO.lstArticles();
    }

    public ArticleVendu trouveParNo(int noArticle) {
        return articleVenduDAO.findById(noArticle);
    }

	public List<ArticleVendu> trouveParCat(String libelle) {
		return articleVenduDAO.findByCat(libelle);
	}

	public void ajouterArticleVendu(ArticleVendu a) {
		articleVenduDAO.add(a);
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

	public List<Categorie> lstCategories() {
		return articleVenduDAO.findAllCategories();
    }


}
