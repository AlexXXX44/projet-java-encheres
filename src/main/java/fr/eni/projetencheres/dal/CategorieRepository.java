package fr.eni.projetencheres.dal;

import fr.eni.projetencheres.bo.Categorie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Integer> {

    Categorie findByLibelle(String string);

    //List<Categorie> findAllCategories();
}
