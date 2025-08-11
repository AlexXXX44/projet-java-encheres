package fr.eni.projetencheres.dal;

import java.util.List;

import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Categorie;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleVenduDAO {

	void add(ArticleVendu a);

	void modifier(ArticleVendu a);

	void delete(ArticleVendu nomArticle);

	ArticleVendu findById(int noArticle);

	List<ArticleVendu> findByCat(String libelle);

	String trouvePseudoParNo(int no);

	String trouveCategorieParNo(int no);

	List<ArticleVendu> lstArticles();

	List<Categorie> findAllCategories();

}


