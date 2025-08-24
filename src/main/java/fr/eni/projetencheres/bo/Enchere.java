package fr.eni.projetencheres.bo;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ENCHERES")
public class Enchere {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_enchere")
    private LocalDateTime dateEnchere;
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

    public Enchere(LocalDateTime dateEnchere, int montantEnchere, Utilisateur utilisateur, ArticleVendu article) {
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

    public LocalDateTime getDateEnchere() {
        return dateEnchere;
    }

    public void setDateEnchere(LocalDateTime dateEnchere) {
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
}
