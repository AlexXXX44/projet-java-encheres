package fr.eni.projetencheres.dal;

import fr.eni.projetencheres.bo.Utilisateur;

import java.util.List;

public interface UtilisateurDao {

    public Utilisateur findByPseudo(String pseudo);
    public List<Utilisateur> findAll();
    public void add(Utilisateur u);
    public void addRole(Utilisateur u);
    public int validateUtilisateurPseudo(String pseudo);
    public int validateUtilisateurEmail(String email);

    public void supprimerUtilisateur(String pseudo);
    public void supprimerRole(String pseudo);

    public void modifierRole(Utilisateur u);

    public void modifierUtilisateur(Utilisateur u);





}
