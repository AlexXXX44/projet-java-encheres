package fr.eni.projetencheres.dal;

public class UtilisateurDto {
    private int noUtilisateur;
    private String telephone;
    private String rue;
    private String ville;
    private String prenom;
    private String nom;
    private String pseudo;
    private String motDePasse;
    private String email;
    private int codePostal;

    // getters et setters

    public String getTelephone() {
        return telephone;
    }

    public String getRue() {
        return rue;
    }

    public String getVille() {
        return ville;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getEmail() {
        return email;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCodePostal() {
        return codePostal;
    }
}

