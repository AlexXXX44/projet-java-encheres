package fr.eni.projetencheres.bo;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ENCHERES")
public class Enchere {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_enchere")
    private LocalDate dateEnchere;
    @Column(name = "montant_enchere")
    private int montantEnchere;

    // relations d'association
    @ManyToOne
    private Utilisateur utilisateur;
    @ManyToOne
    private ArticleVendu article;

    /**
     * constructeurs
     */
    public Enchere() {
    }

    public Enchere(LocalDate dateEnchere, int montantEnchere, Utilisateur utilisateur, ArticleVendu article) {
        this.dateEnchere = dateEnchere;
        this.montantEnchere = montantEnchere;
        this.utilisateur = utilisateur;
        this.article = article;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Enchere [").append(dateEnchere).append(", ").append(montantEnchere).append(", ")
                .append(utilisateur).append(", ").append(article).append("]");
        return builder.toString();
    }

    public Enchere(int montantEnchere, Utilisateur utilisateur, ArticleVendu article) {
        this.montantEnchere = montantEnchere;
        this.utilisateur = utilisateur;
        this.article = article;
    }

    public Enchere(LocalDate dateEnchere, int montantEnchere) {
        this.dateEnchere = dateEnchere;
        this.montantEnchere = montantEnchere;
    }

    public Enchere(int montantEnchere) {
        this.montantEnchere = montantEnchere;
    }

    public Enchere(LocalDate dateEnchere) {
        this.dateEnchere = dateEnchere;
    }

    public Enchere(int montantEnchere, Utilisateur utilisateur) {
        this.montantEnchere = montantEnchere;
        this.utilisateur = utilisateur;
    }

    public Enchere(LocalDate dateEnchere, Utilisateur utilisateur) {
        this.dateEnchere = dateEnchere;
        this.utilisateur = utilisateur;
    }

    public Enchere(int montantEnchere, ArticleVendu article) {
        this.montantEnchere = montantEnchere;
        this.article = article;
    }

    public Enchere(LocalDate dateEnchere, ArticleVendu article) {
        this.dateEnchere = dateEnchere;
        this.article = article;
    }

    public Enchere(Utilisateur utilisateur, ArticleVendu article) {
        this.utilisateur = utilisateur;
        this.article = article;
    }

    public Enchere(LocalDate dateEnchere, int montantEnchere, Utilisateur utilisateur) {
        this.dateEnchere = dateEnchere;
        this.montantEnchere = montantEnchere;
        this.utilisateur = utilisateur;
    }

    public Enchere(LocalDate dateEnchere, int montantEnchere, ArticleVendu article) {
        this.dateEnchere = dateEnchere;
        this.montantEnchere = montantEnchere;
        this.article = article;
    }

    public int getNoEnchere() {
        return id.intValue();
    }
    
    public LocalDate getDateEnchere() {
        return dateEnchere;
    }

    public void setDateEnchere(LocalDate dateEnchere) {
        this.dateEnchere = dateEnchere;
    }

    public int getMontantEnchere() {
        return montantEnchere;
    }

    public void setMontantEnchere(int montantEnchere) {
        this.montantEnchere = montantEnchere;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public ArticleVendu getArticle() {
        return article;
    }

    public void setArticleVendu(ArticleVendu article2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setArticleVendu'");
    }

    public void setUtilisateur(Utilisateur utilisateur2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setUtilisateur'");
    }

    public static void faireEnchere(Utilisateur utilisateur2, ArticleVendu article2, int montantEncheres) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'faireEnchere'");
    }
}
