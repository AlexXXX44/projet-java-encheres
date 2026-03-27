package fr.eni.projetencheres.bo;

import fr.eni.projetencheres.dal.UtilisateurDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "UTILISATEURS")
public class Utilisateur extends UtilisateurDto implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "no_utilisateur")
	private int noUtilisateur;

	@Pattern(regexp = "[A-Za-z0-9]+", message = "Pseudo non valide")
	@Column(name = "pseudo", nullable = false)
	private String pseudo;

	@NotBlank(message = "Le nom obligatoire")
	@Column(name = "nom", nullable = false)
	private String nom;

	@NotBlank(message = "Le prénom obligatoire")
	@Column(name = "prenom", nullable = false)
	private String prenom;

	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Email non valide")
	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Pattern(regexp = "0[1-9][0-9]{8}", message = "Téléphone non valide")
	@Column(name = "telephone")
	private String telephone;

	@NotBlank(message = "La rue obligatoire")
	@Column(name = "rue", nullable = false)
	private String rue;

	@NotBlank(message = "La ville obligatoire")
	@Column(name = "ville", nullable = false)
	private String ville;

	@NotNull(message = "Le code postal est obligatoire")
	@Column(name = "code_postal", nullable = false)
	private int codePostal;

	@NotBlank(message = "Le mot de passe obligatoire")
	@Column(name = "mot_de_passe", nullable = false)
	private String motDePasse;

	@Column(name = "credit", nullable = false)
	private int credit;

	@Column(name = "administrateur", nullable = false)
	private boolean administrateur;

	// --- UserDetails implémentation ---
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override public boolean isAccountNonExpired() { return true; }
	@Override public boolean isAccountNonLocked() { return true; }
	@Override public boolean isCredentialsNonExpired() { return true; }
	@Override public boolean isEnabled() { return true; }


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
		System.out.println("SET TELEPHONE = " + telephone);

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
		return this.administrateur;
	}

	public int getNoUtilisateur() {
        return noUtilisateur;
    }

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getPassword'");
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getUsername'");
	}
}

