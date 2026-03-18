package fr.eni.projetencheres.bo;

import jakarta.persistence.*;

@Entity
@Table(name = "ROLES")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pseudo;
    private String role;

    public Role() {}

    public Role(String pseudo, String role) {
        this.pseudo = pseudo;
        this.role = role;
    }

    public Long getId() { return id; }
    public String getPseudo() { return pseudo; }
    public void setPseudo(String pseudo) { this.pseudo = pseudo; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}