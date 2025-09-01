
package fr.eni.projetencheres.dal;

import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Enchere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnchereRepository extends JpaRepository<Enchere, Integer> {
    List<Enchere> findByArticleOrderByMontantEnchereDesc(ArticleVendu article);

    List<Enchere> findByUtilisateur_NoUtilisateur(int noUtilisateur);
}
