package fr.eni.projetencheres.dal;

import java.util.List;

import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Categorie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleVenduDAO extends JpaRepository<ArticleVendu, Integer> {

	void delete(ArticleVendu nomArticle);

	ArticleVendu findById(int noArticle);

	Page<ArticleVendu> findByNoCategorie_NoCategorie(Integer noCategorie, Pageable pageable);
}


