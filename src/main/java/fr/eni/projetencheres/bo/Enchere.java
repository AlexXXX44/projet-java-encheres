package fr.eni.projetencheres.bo;

import java.time.LocalDateTime;

public class Enchere {

	private LocalDateTime dateEnchere;
	private int montantEnchere;

	// relations d'association
	private Utilisateur noUtilisateur;
	private ArticleVendu noArticle;

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

}
