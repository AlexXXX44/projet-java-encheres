package fr.eni.projetencheres.bo;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="ARTICLES_VENDUS")
public class ArticleVendu {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "no_article")
	private int noArticle;

	@Column(name = "nom_article")
	private String nomArticle;

	@Column(name = "description")
	private String description;

	@Column(name = "etat_vente")
	private String etatVente;
	@Column(name = "date_debut_encheres")
	private LocalDate dateDebutEncheres;

	@Column(name = "date_fin_encheres")
	private LocalDate dateFinEncheres;

	@Column(name = "mise_a_prix")
	private int miseAPrix;

	@Column(name = "prix_vente")
	private int prixVente;

	// relations d'association

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "utilisateur", referencedColumnName = "no_utilisateur", nullable = false)
	private Utilisateur vendeur;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "no_categorie", referencedColumnName = "no_categorie", nullable = false)
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