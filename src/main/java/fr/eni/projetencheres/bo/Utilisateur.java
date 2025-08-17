package fr.eni.projetencheres.bo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Table(name = "UTILISATEURS")
public class Utilisateur {
	@jakarta.persistence.Id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int noUtilisateur;

	@Pattern(regexp = "[A-Za-z0-9]+", message = "Pseudo non valide")
	private String pseudo;
	@NotBlank(message = "Le nom obligatoire")
	private String nom;
	@NotBlank(message = "Le prénom obligatoire")
	private String prenom;
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Email non valide")
	private String email;
	@Pattern(regexp = "^\\d{2}(\\.\\d{2}){4}$", message = "Téléphone non valide")
	private String telephone;


	@NotBlank(message = "La rue obligatoire")
	private String rue;

	@NotBlank(message = "La ville obligatoire")
	private String ville;
	@NotNull(message = "Le code postal est obligatoire")
	private int codePostal;
	@NotBlank(message = "Le mot de passe obligatoire")
	private String motDePasse;
	private int credit;
	private boolean administrateur;

	/**
	 * constructeurs
	 */
	public Utilisateur() {
	}

	public Utilisateur(int noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone,
					   String rue, String ville, int codePostal, String motDePasse, int credit, boolean administrateur) {

		this.noUtilisateur = noUtilisateur;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.ville = ville;
		this.codePostal = codePostal;
		this.motDePasse = motDePasse;
		this.credit = credit;
		this.administrateur = administrateur;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[").append(noUtilisateur).append(", ").append(pseudo).append(", ").append(nom)
				.append(", ").append(prenom).append(", ").append(email).append(", ").append(telephone).append(", ")
				.append(rue).append(", ").append(ville).append(", ").append(codePostal).append(", ").append(motDePasse)
				.append(", ").append(credit).append(", ").append(administrateur).append("]");
		return builder.toString();
	}


	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public int getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(int codePostal) {
		this.codePostal = codePostal;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public boolean getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(boolean administrateur) {
		this.administrateur = administrateur;
	}

	public boolean isAdmin() {
		return false;
	}

	public int getNoUtilisateur() {
        return noUtilisateur;
    }
}

