package fr.eni.projetencheres.dal;

import fr.eni.projetencheres.bo.ArticleVendu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.net.ContentHandler;

public interface ArticleVenduRepository extends JpaRepository<ArticleVendu, Integer> {

    Page<ArticleVendu> findByNomArticleContainingIgnoreCase(String nomArticle, Pageable pageable);

    Page<ArticleVendu> findBynoCategorieAndNomArticleContainingIgnoreCase(
            Integer noCategorie, String nomArticle, Pageable pageable
    );
}
