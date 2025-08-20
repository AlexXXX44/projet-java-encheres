package fr.eni.projetencheres.bo;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="ARTICLES_VENDUS")
public class ArticleVendu {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int noArticle;
	private String nomArticle;
	private String description;
	private String etatVente;
	private LocalDate dateDebutEncheres;
	private LocalDate dateFinEncheres;
	private int miseAPrix;
	private int prixVente;

	// relations d'association
	@ManyToOne
	@JoinColumn(name = "utilisateur_no_utilisateur")
	private Utilisateur noUtilisateur;
	@ManyToOne
	private Categorie noCategorie;

	public ArticleVendu() {
	}

	// Getters / Setters requis par Thymeleaf (JavaBean)

	public int getNoArticle() {
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

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEtatVente() {
		return etatVente;
	}

	public void setEtatVente(String etatVente) {
		this.etatVente = etatVente;
	}

	public LocalDate getDateDebutEncheres() {
		return dateDebutEncheres;
	}

	public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}

	public LocalDate getDateFinEncheres() {
		return dateFinEncheres;
	}

	public void setDateFinEncheres(LocalDate dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}

	public int getMiseAPrix() {
		return miseAPrix;
	}

	public void setMiseAPrix(int miseAPrix) {
		this.miseAPrix = miseAPrix;
	}

	public int getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}

	public void setNoUtilisateur(Utilisateur noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	public void setNoCategorie(Categorie noCategorie) {
		this.noCategorie = noCategorie;
	}

	public Categorie getCategorie() {
		return noCategorie;
	}

	public Categorie getNoCategorie() {
		return noCategorie;
	}

	public Utilisateur getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setCategorie(Categorie noCategorie) {
		this.noCategorie = noCategorie;
	}
}