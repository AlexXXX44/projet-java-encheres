package fr.eni.projetencheres.bo;

import java.time.LocalDate;

public class ArticleVendu {

	private int noArticle;
	private String nomArticle;
	private String description;
	private String etatVente;
	private LocalDate dateDebutEncheres;
	private LocalDate dateFinEncheres;
	private int miseaPrix;
	private int prixVente;

	// relations d'association
	private Utilisateur utilisateur;
	private Categorie categorie;
	/**
	 * constructeurs
	 */
	public ArticleVendu() {
	}

	public ArticleVendu(int noArticle, String nomArticle, String description, String etatVente,
						LocalDate dateDebutEncheres, LocalDate dateFinEncheres, int miseaPrix, int prixVente,
			Utilisateur utilisateur, Categorie categorie) {

		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.etatVente = etatVente;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseaPrix = miseaPrix;
		this.prixVente = prixVente;
		this.utilisateur = utilisateur;
		this.categorie = categorie;
	}


	@Override
	public String toString() {
        return "ArticleVendu [noArticle=" + noArticle + ", nomArticle=" + nomArticle +
                ", dateFinEncheres=" + dateFinEncheres + ", prixInitial=" + miseaPrix +
                ", prixVente=" + prixVente + ", libelle=" + categorie.getLibelle() +
                "]";
	}

	public String getEtatVente() {
		return etatVente;
	}

	public void setEtatVente(String etatVente) {
		this.etatVente = etatVente;
	}

	public Object getNoArticle() {
		return noArticle;
	}

	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}

	public String getNomArticle() {
		return nomArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getDateDebutEncheres() {
		return dateDebutEncheres;
	}

	public LocalDate getDateFinEncheres() {
		return dateFinEncheres;
	}

	public void setDateFinEncheres(LocalDate dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}

	public int getMiseaPrix() {
		return miseaPrix;
	}

	public int getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie libelle) {
		this.categorie = libelle;
	}
}
