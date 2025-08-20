package fr.eni.projetencheres.bo;

import jakarta.persistence.*;

@Entity
@Table(name = "CATEGORIES")
public class Categorie {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int noCategorie;
	private String libelle;

	/**
	 * constructeurs
	 */
	public Categorie() {
	}

	public Categorie(int noCategorie, String libelle) {
		this.noCategorie = noCategorie;
		this.libelle = libelle;

	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[").append(libelle).append("]");
		return builder.toString();
	}

	public int getNoCategorie() {
		return noCategorie;
	}

	public void setNoCategorie(int noCategorie) {
		this.noCategorie = noCategorie;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

}
