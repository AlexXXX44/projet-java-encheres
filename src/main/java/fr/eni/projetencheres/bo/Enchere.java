package fr.eni.projetencheres.bo;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ENCHERES")
public class Enchere {

	private LocalDateTime dateEnchere;
	private int montantEnchere;

	// relations d'association
	@ManyToOne
	@JoinColumn(name = "no_utilisateur_no_utilisateur")
	private Utilisateur noUtilisateur;
	@ManyToOne
	@JoinColumn(name = "no_article_no_article")
	private ArticleVendu noArticle;
    @Id
    private Long id;

	/**
	 * constructeurs
	 */
	public Enchere() {
	}

	public Enchere(LocalDateTime dateEnchere, int montantEnchere, Utilisateur noUtilisateur, ArticleVendu noArticle) {

		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
		this.noUtilisateur = noUtilisateur;
		this.noArticle = noArticle;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Enchere [").append(dateEnchere).append(", ").append(montantEnchere).append(", ")
				.append(noUtilisateur).append(", ").append(noArticle).append("]");
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

	public Utilisateur getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(Utilisateur noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	public ArticleVendu getNoArticle() {
		return noArticle;
	}

	public void setNoArticle(ArticleVendu noArticle) {
		this.noArticle = noArticle;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
