package fr.eni.projetencheres.dal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.projetencheres.bo.Utilisateur;

@SuppressWarnings("unused")

@Repository
public class UtilisateurDaoJdbcImpl implements UtilisateurDao {

    private static final PreparedStatementCreator INSERT_ROLE = null;
    private static final PreparedStatementCreator SUPPRIMER_ROLE = null;
    private static final PreparedStatementCreator MODIFIER_ROLE = null;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final static String FIND_BY_PSEUDO= "SELECT u.no_Utilisateur, u.pseudo, u.nom, u.prenom, u.email, u.telephone, " +
            "u.rue, u.code_postal, u.ville, u.mot_de_passe, u.credit, u.administrateur" +
            " FROM UTILISATEURS u " +
            "WHERE u.pseudo=?";
    private final static String FIND_ALL= "SELECT u.no_utilisateur, u.pseudo, u.nom, u.prenom, u.email, u.telephone, " +
            "u.rue, u.code_postal, u.ville, u.mot_de_passe, u.credit, u.administrateur" +
            " FROM UTILISATEURS u ";
    private final static String INSERT = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe,credit,administrateur) "
            +"VALUES (:pseudo, :nom, :prenom, :email, :telephone, :rue, :codePostal, :ville, :motDePasse, :credit, :administrateur) ";

//    private final static String INSERT_ROLE = "INSERT INTO ROLES(pseudo, role, nu_utilisateur) VALUES(?,?,?)";

    private final static String FIND_PSEUDO_NUMBER = "SELECT COUNT(*) FROM UTILISATEURS WHERE pseudo = ?";

    private final static String FIND_EMAIL_NUMBER = "SELECT COUNT(*) FROM UTILISATEURS WHERE email = ?";


    private final static String MODIFIER_PROFIL = "UPDATE UTILISATEURS SET pseudo=?, " +
            "nom=?, prenom=?, email=?, telephone=?, rue=?, code_postal=?, ville=?, mot_de_passe=? " +
            "WHERE no_utilisateur = ?";
//    private final static String MODIFIER_ROLE = "UPDATE ROLES SET pseudo=? WHERE nu_utilisateur = ?";

    private final static String SUPPRIMER_UTILISATEUR = "DELETE FROM UTILISATEURS WHERE pseudo = ?";
//    private final static String SUPPRIMER_ROLE = "DELETE FROM ROLES WHERE pseudo = ?";

    @Override
    public Utilisateur findByPseudo(String pseudo) {
        return (Utilisateur) jdbcTemplate.queryForObject(FIND_BY_PSEUDO, new BeanPropertyRowMapper<>(Utilisateur.class),
                pseudo);
    }

    @Override
    public List<Utilisateur> findAll() {
        return jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Utilisateur.class));
    }

    @Override
    public void add(Utilisateur u) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pseudo", u.getPseudo());
        params.addValue("nom", u.getNom());
        params.addValue("prenom", u.getPrenom());
        params.addValue("email", u.getEmail());
        params.addValue("telephone", u.getTelephone());
        params.addValue("rue", u.getRue());
        params.addValue("ville", u.getVille());
        params.addValue("codePostal", u.getCodePostal());
        params.addValue("motDePasse", u.getMotDePasse());
        params.addValue("credit", u.getCredit());
        params.addValue("administrateur", u.getAdministrateur());
        namedParameterJdbcTemplate.update(INSERT, params, keyHolder);

        if(keyHolder.getKey() != null){
            int nouvId = keyHolder.getKey().intValue();
            u.setNoUtilisateur(nouvId);}
        addRole(u);
    }

    public void addRole(Utilisateur u) {
        jdbcTemplate.update(String.valueOf(INSERT_ROLE), u.getPseudo(),"ROLE_USER", u.getNoUtilisateur());
    }

   @Override
   public void modifierUtilisateur(Utilisateur u) {
       jdbcTemplate.update(MODIFIER_PROFIL,  u.getPseudo(), u.getNom(), u.getPrenom(), u.getEmail(),
               u.getTelephone(), u.getRue(), u.getCodePostal(), u.getVille(), u.getMotDePasse(), u.getNoUtilisateur());
    }
    @Override
    public void modifierRole(Utilisateur u) {
        jdbcTemplate.update(String.valueOf(MODIFIER_ROLE), u.getPseudo(), u.getNoUtilisateur());
    }
    @Override
    public int validateUtilisateurPseudo(String pseudo) {
        return jdbcTemplate.queryForObject(FIND_PSEUDO_NUMBER, Integer.class, pseudo);
}

    @Override
    public int validateUtilisateurEmail(String email) {
        return jdbcTemplate.queryForObject(FIND_EMAIL_NUMBER, Integer.class, email);
    }


    @Override
    public void supprimerUtilisateur(String pseudo) {
        jdbcTemplate.update(SUPPRIMER_UTILISATEUR, pseudo);
    }

    @Override
    public void supprimerRole(String pseudo) {
        jdbcTemplate.update(String.valueOf(SUPPRIMER_ROLE), pseudo);
    }




    //    @Override
//    public void modifierUtilisateur(Utilisateur u) {
//        MapSqlParameterSource params = new MapSqlParameterSource();
//        params.addValue("pseudo", u.getPseudo());
//        params.addValue("nom", u.getNom());
//        params.addValue("prenom", u.getPrenom());
//        params.addValue("email", u.getEmail());
//        params.addValue("telephone", u.getTelephone());
//        params.addValue("rue", u.getRue());
//        params.addValue("ville", u.getVille());
//        params.addValue("codePostal", u.getCodePostal());
//        params.addValue("motDePasse", u.getMotDePasse());
//        params.addValue("credit", u.getCredit());
//        params.addValue("administrateur", u.getAdministrateur());
//        namedParameterJdbcTemplate.update(MODIFIER_PROFIL, params);
//    }
}
