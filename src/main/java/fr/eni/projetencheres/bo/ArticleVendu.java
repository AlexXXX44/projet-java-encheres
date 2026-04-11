package fr.eni.projetencheres.bo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name="ARTICLES_VENDUS")
public class ArticleVendu {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "no_article")
	private int noArticle;

	@Column(name = "nom_article")
	@NotBlank(message = "Nom obligatoire")
	private String nomArticle;

	@Column(name = "description")
	@NotBlank(message = "Description obligatoire")
	private String description;

	//EN_COURS, TERMINEE, NON_DEMARREE
	@Column(name = "etat_vente")
	private String etatVente;

	@Column(name = "mise_a_prix")
	@Min(value = 1, message = "Prix minimum 1")
	private int miseAPrix;

	@Column(name = "date_debut_encheres")
	@NotNull(message = "Date début obligatoire")
	private LocalDate dateDebutEncheres;

	@Column(name = "date_fin_encheres")
	@NotNull(message = "Date fin obligatoire")
	private LocalDate dateFinEncheres;

	@Column(name = "prix_vente")
	private int prixVente;

	// relations d'association

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "utilisateur", referencedColumnName = "no_utilisateur", nullable = false)
	private Utilisateur vendeur;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "no_categorie", referencedColumnName = "no_categorie", nullable = false)
	private Categorie noCategorie;

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

	public void setNoCategorie(Categorie noCategorie) {
		this.noCategorie = noCategorie;
	}

	public Categorie getCategorie() {
		return noCategorie;
	}

	public Categorie getNoCategorie() {
		return noCategorie;
	}

	public void setCategorie(Categorie noCategorie) {
		this.noCategorie = noCategorie;
	}

	public Utilisateur getVendeur() {
		return vendeur;
	}

    public void setVendeur(Utilisateur u) {
            this.vendeur = u;
	   }
}