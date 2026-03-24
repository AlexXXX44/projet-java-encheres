package fr.eni.projetencheres.dal;

import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Categorie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleVenduRepository extends JpaRepository<ArticleVendu, Integer> {

    @Query("""
        SELECT a FROM ArticleVendu a
        WHERE (:keyword IS NULL OR LOWER(a.nomArticle) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(a.description) LIKE LOWER(CONCAT('%', :keyword, '%')))
          AND (:noCategorie IS NULL OR a.noCategorie.noCategorie = :noCategorie)
    """)
    List<ArticleVendu> search(@Param("keyword") String keyword,
                              @Param("noCategorie") Integer noCategorie);

    // autres méthodes inchangées
    List<ArticleVendu> findByNoCategorie_Libelle(Integer noCategorie, PageRequest pageRequest);

    Page<ArticleVendu> findByNomArticleContainingIgnoreCase(String nomArticle, Pageable pageable);

    // Respecter la casse pour la déduction de propriétés (NoCategorie)
    Page<ArticleVendu> findBynoCategorieAndNomArticleContainingIgnoreCase(
            Integer noCategorie, String nomArticle, Pageable pageable
    );

    Slice<ArticleVendu> findByNoCategorie_NoCategorie(Integer noCategorie, PageRequest of);
}