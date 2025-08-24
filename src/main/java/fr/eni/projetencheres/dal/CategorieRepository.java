package fr.eni.projetencheres.dal;

import fr.eni.projetencheres.bo.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategorieRepository extends JpaRepository<Categorie, Integer> {
}
