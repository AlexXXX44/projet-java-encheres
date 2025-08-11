package fr.eni.projetencheres.bo;

import java.util.Date;

public class ArticleVendu {

	private int noArticle;
	private String nomArticle;
	private String description;
	private String etatVente;
	private Date dateDebutEncheres;
	private Date dateFinEncheres;
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
						Date dateDebutEncheres, Date dateFinEncheres, int miseaPrix, int prixVente,
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
		StringBuilder builder = new StringBuilder();
		builder.append("ArticleVendu [noArticle=").append(noArticle).append(", nomArticle=").append(nomArticle)
				.append(", dateFinEncheres=").append(dateFinEncheres).append(", prixInitial=").append(miseaPrix)
				.append(", prixVente=").append(prixVente).append(", libelle=").append(categorie.getLibelle())
				.append("]");
		return builder.toString();
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

	public Date getDateDebutEncheres() {
		return dateDebutEncheres;
	}

	public Date getDateFinEncheres() {
		return dateFinEncheres;
	}

	public void setDateFinEncheres(Date dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}

	public int getMiseaPrix() {
		return miseaPrix;
	}

	public void setMiseaPrix(int miseAPRix) {
		this.miseaPrix = miseAPRix;
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
