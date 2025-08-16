/*package fr.eni.projetencheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Categorie;
import fr.eni.projetencheres.bo.Utilisateur;

@Repository
public class ArticleVenduDAOJdbcImpl implements ArticleVenduDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final static String MODIFIER_ARTICLE = "UPDATE TABLE article_vendu a SET WHERE no_article = ?";

    private final static String SUPPRIMER_ARTICLE = "DELETE FROM TABLE article_vendu a WHERE no_articlce =?";

    private final static String AJOUTER_ARTICLE = "INSERT INTO article_vendu (no_article, nom_article, description,"
            + " date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)"
            + "	VALUES (?,?,?,?,?,?,?,?,?)";

    private final static String FIND_ALL = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres,"
            + " a.date_fin_encheres, a.miseaprix, a.prix_vente, a.vendeur_no_utilisateur, a.categorie_no_categorie"
            + " FROM article_vendu a INNER JOIN utilisateurs u ON a.vendeur_no_utilisateur = u.no_utilisateur";

    private final static String FIND_BY_ID = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres,"
            + " a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_utilisateur, a.categorie_no_categorie"
            + " FROM article_vendu a INNER JOIN utilisateurs u ON a.no_utilisateur = u.no_utilisateur WHERE no_article = ?";

    private final static String FIND_BY_CAT = "SELECT a.no_article, a.nom_article, a.description,"
            + " a.date_debut_encheres, a.date_fin_encheres,"
            + " a.miseaprix, a.prix_vente,"
            + " a.vendeur_no_utilisateur, a.categorie_no_categorie"
            + " FROM article_vendu a"
            + " INNER JOIN utilisateurs u"
            + " ON a.vendeur_no_utilisateur = u.no_utilisateur"
            + " WHERE a.categorie_no_categorie = ?";

    private final static String FIND_CATEGORIE_BY_ID = "SELECT c.libelle FROM categorie c INNER JOIN article_vendu a"
            + " ON c.no_categorie = a.categorie_no_categorie WHERE no_article = ?";

    private final static String FIND_PSEUDO_BY_ID = "SELECT u.pseudo FROM utilisateurs u INNER JOIN article_vendu a"
            + " ON a.vendeur_no_utilisateur = u.no_utilisateur WHERE no_article = ?";

    private final static String FIND_ALL_CATEGORIES = "SELECT no_categorie, libelle FROM categorie";

    @Override
    public List<ArticleVendu> findArticles(int page, int size, String sortBy, String sortDir) {
        String sql = "SELECT * FROM ARTICLES_VENDUS ORDER BY " + sortBy + " " + sortDir +
                " LIMIT ? OFFSET ?";

        int offset = (page - 1) * size;

        return jdbcTemplate.query(sql, new ArticleVenduRowMapper(), size, offset);
    }

    public List<ArticleVendu> lstArticles() {
        return jdbcTemplate.query(FIND_ALL, new ArticleVenduRowMapper());
    }

    public ArticleVendu findById(int noArticle) {
        return jdbcTemplate.queryForObject(FIND_BY_ID, new ArticleVenduRowMapper());
    }

    @Override
    public List<ArticleVendu> findByCat(String libelle) {
        return jdbcTemplate.query(FIND_BY_CAT, new ArticleVenduRowMapper(), libelle);
    }

//    public List<ArticleVendu> findByCat(int noCategorie) {
//        return jdbcTemplate.query(FIND_BY_CAT, new ArticleVenduRowMapper(), noCategorie);
//    }

    public String trouvePseudoParNo(int noArticle) {
        return jdbcTemplate.queryForObject(FIND_PSEUDO_BY_ID, String.class);
    }

    public String trouveCategorieParNo(int noArticle) {
        return jdbcTemplate.queryForObject(FIND_CATEGORIE_BY_ID, String.class);
    }

    @Override
    public void add(ArticleVendu a) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(AJOUTER_ARTICLE, Optional.of(a.getNoArticle()));
        params.addValue(AJOUTER_ARTICLE, a.getNomArticle());
        params.addValue(AJOUTER_ARTICLE, a.getDescription());
        params.addValue(AJOUTER_ARTICLE, a.getDateDebutEncheres());
        params.addValue(AJOUTER_ARTICLE, a.getDateFinEncheres());
        params.addValue(AJOUTER_ARTICLE, Optional.of(a.getMiseaPrix()));
        params.addValue(AJOUTER_ARTICLE, Optional.of(a.getPrixVente()));
        params.addValue(AJOUTER_ARTICLE, a.getUtilisateur());
        params.addValue(AJOUTER_ARTICLE, a.getUtilisateur());

        namedParameterJdbcTemplate.update(AJOUTER_ARTICLE, params);
    }

    @Override
    public void delete(ArticleVendu a) {
        jdbcTemplate.update(SUPPRIMER_ARTICLE, a);
    }

    @Override
    public void modifier(ArticleVendu a) {
        jdbcTemplate.update(MODIFIER_ARTICLE, a.getNoArticle(), a.getNomArticle(), a.getDescription(),
                a.getDateDebutEncheres(), a.getDateFinEncheres(), a.getMiseaPrix(), a.getPrixVente(),
                a.getUtilisateur(), a.getCategorie());
    }

    @Override
    public List<Categorie> findAllCategories() {
        return jdbcTemplate.query(FIND_ALL_CATEGORIES, new BeanPropertyRowMapper<>(Categorie.class));
    }

    static class ArticleVenduRowMapper implements RowMapper<ArticleVendu> {

        @Override
        public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException {
            ArticleVendu a = new ArticleVendu();
            a.setNoArticle(rs.getInt("no_article"));
            // a.setEtatVente(rs.getString("etat_de_la_vente"));
            // a.setLibelle(rs.getString("libelle"));
            a.setNomArticle(rs.getString("nom_article"));
            // a.setDescription(rs.getString("description"));
            // a.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
            a.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
            // a.setPrixInitial(rs.getInt("prix_initial"));
            a.setPrixVente(rs.getInt("prix_vente"));

            Utilisateur u = new Utilisateur();

            // u.setNoUtilisateur(rs.getInt("no_utilisateur"));
            // u.setPseudo(rs.getString("pseudo"));
            // u.setNom(rs.getString("nom"));
            // u.setPrenom(rs.getString("prenom"));
            // u.setTelephone(rs.getString("telephone"));
            // u.setRue(rs.getString("codePostal"));
            // u.setVille(rs.getString("ville"));
            // u.setMotDePasse(rs.getString("mot_de_passe"));
            // u.setCredit(rs.getInt("credit"));
            // u.setAdministrateur(rs.getBoolean("administrateur"));

            a.setUtilisateur(u);

            Categorie c = new Categorie();
            c.setNoCategorie(rs.getInt("no_categorie"));
            // c.setLibelle(rs.getString("libelle"));

            a.setCategorie(c);

            return a;
        }
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ArticleVendu> findArticles(int page, int size, String sortBy, String sortDir) {
        // Sécurité pour éviter les injections SQL dans sortBy / sortDir
        if (!sortBy.matches("^[a-zA-Z_]+$")) {
            sortBy = "date_fin_encheres";
        }
        if (!sortDir.equalsIgnoreCase("asc") && !sortDir.equalsIgnoreCase("desc")) {
            sortDir = "asc";
        }

        String sql = "SELECT * FROM article_vendu " +
                "ORDER BY " + sortBy + " " + sortDir +
                " LIMIT ? OFFSET ?";

        int offset = (page - 1) * size;

        return jdbcTemplate.query(sql, new ArticleVenduRowMapper(), size, offset);
    }

}*/
//package fr.eni.projetencheres.dal.jdbc;
package fr.eni.projetencheres.dal;

import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Categorie;
import fr.eni.projetencheres.bo.Utilisateur;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ArticleVenduDAOJdbcImpl implements ArticleVenduDAO {

    private final JdbcTemplate jdbcTemplate;

    public ArticleVenduDAOJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ArticleVendu findById(int noArticle) {
        String sql = "SELECT a.no_article, a.nom_article, a.description, a.date_fin_encheres, " +
                "a.prix_initial, c.libelle AS categorie, u.pseudo " +
                "FROM article_vendu a " +
                "JOIN categorie c ON a.no_categorie = c.no_categorie " +
                "JOIN utilisateurs u ON a.no_utilisateur = u.no_utilisateur " +
                "WHERE a.no_article = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{noArticle}, (rs, rowNum) -> {
            ArticleVendu article = new ArticleVendu();
            article.setNoArticle(rs.getInt("no_article"));
            article.setNomArticle(rs.getString("nom_article"));
            article.setDescription(rs.getString("description"));
            article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
            article.setMiseAPrix(rs.getInt("mise_a_prix"));
            article.getCategorie().setLibelle(rs.getString("categorie")); // il faut avoir cet attribut ou un setter
            article.getUtilisateur().setPseudo(rs.getString("pseudo")); // idem, à prévoir dans ton BO
            return article;
        });
    }


    @Override
    public List<ArticleVendu> lstArticles() {
        String sql = """
        SELECT a.no_article, a.nom_article, a.description, a.etat_vente,
               a.date_debut_encheres, a.date_fin_encheres,
               a.mise_a_prix, a.prix_vente,
               u.no_utilisateur, u.pseudo AS pseudo_vendeur,
               c.no_categorie, c.libelle AS libelle_categorie
        FROM article_vendu a
        JOIN utilisateurs u ON a.no_utilisateur = u.no_utilisateur
        JOIN categorie c ON a.no_categorie = c.no_categorie
        """;

        return jdbcTemplate.query(sql, new ArticleVenduRowMapper());
    }


    @Override
    public List<Categorie> findAllCategories() {
        String sql = "SELECT no_categorie, libelle FROM categorie";
        return jdbcTemplate.query(sql, new CategorieRowMapper());
    }

    // Mapper Categorie
    public static class CategorieRowMapper implements RowMapper<Categorie> {

        @Override
        public Categorie mapRow(ResultSet rs, int rowNum) throws SQLException {
            Categorie c = new Categorie();
            c.setNoCategorie(rs.getInt("no_categorie"));
            c.setLibelle(rs.getString("libelle"));
            return c;
        }
    }

    // MapperArticle
    public static class ArticleVenduRowMapper implements RowMapper<ArticleVendu> {

        @Override
        public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException {
            ArticleVendu a = new ArticleVendu();

            a.setNoArticle(rs.getInt("no_article"));
            a.setNomArticle(rs.getString("nom_article"));
            a.setDescription(rs.getString("description"));
            a.setEtatVente(rs.getString("etat_vente"));

            // ✅ Dates
            a.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
            a.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());

            a.setMiseAPrix(rs.getInt("mise_a_prix"));
            a.setPrixVente(rs.getInt("prix_vente"));

            // ✅ Associer utilisateur
            Utilisateur u = new Utilisateur();
            u.setNoUtilisateur(rs.getInt("no_utilisateur"));
            u.setPseudo(rs.getString("pseudo_vendeur")); // alias SQL
            a.setUtilisateur(u);

            // ✅ Associer catégorie
            Categorie c = new Categorie();
            c.setNoCategorie(rs.getInt("no_categorie"));
            c.setLibelle(rs.getString("libelle_categorie")); // alias SQL
            a.setCategorie(c);

            return a;
        }
    }

//    @Override
//    public List<ArticleVendu> findByCat(String libelle) {
//        String sql = """
//            SELECT a.*, u.pseudo, c.libelle
//            FROM article_vendu a
//            JOIN utilisateur u ON a.vendeur_no_utilisateur = u.no_utilisateur
//            JOIN categorie c ON a.categorie_no_categorie = c.no_categorie
//            WHERE c.libelle = ?
//        """;
//        return jdbcTemplate.query(sql, ArticleVenduRowMapper, libelle);
//    }

//    @Override
//    public List<ArticleVendu> lstArticles() {
//        String sql = """
//            SELECT a.*, u.pseudo, c.libelle
//            FROM article_vendu a
//            JOIN utilisateur u ON a.vendeur_no_utilisateur = u.no_utilisateur
//            JOIN categorie c ON a.categorie_no_categorie = c.no_categorie
//        """;
//        return jdbcTemplate.query(sql, articleMapper);
//    }

//    @Override
//    public List<ArticleVendu> findByCat(String libelle) {
//        List<ArticleVendu> articles = new ArrayList<>();
//        String sql = """
//        SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres,
//               a.miseaprix, a.prix_vente, a.etat_vente,
//               c.no_categorie, c.libelle AS categorie_libelle,
//               u.no_utilisateur, u.pseudo
//        FROM articles_vendus a
//        JOIN categories c ON a.no_categorie = c.no_categorie
//        JOIN utilisateurs u ON a.no_utilisateur = u.no_utilisateur
//        WHERE c.libelle = ?
//    """;
//
//        try {
//            articles = jdbcTemplate.query(sql, new Object[]{libelle}, (rs, rowNum) -> {
//                ArticleVendu article = new ArticleVendu();
//                article.setNoArticle(rs.getInt("no_article"));
//                article.setNomArticle(rs.getString("nom_article"));
//                article.setDescription(rs.getString("description"));
//                article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
//                article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
//                article.setMiseaPrix(rs.getInt("prix_initial"));
//                article.setPrixVente(rs.getInt("prix_vente"));
//                article.setEtatVente(rs.getString("etat_vente"));
//
//                 Catégorie
//                Categorie cat = new Categorie();
//                cat.setNoCategorie(rs.getInt("no_categorie"));
//                cat.setLibelle(rs.getString("categorie_libelle"));
//                article.setCategorie(cat);
//
                // Utilisateur
//                Utilisateur u = new Utilisateur();
//                u.setNoUtilisateur(rs.getInt("no_utilisateur"));
//                u.setPseudo(rs.getString("pseudo"));
//                article.setUtilisateur(u);
//
//                return article;
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return articles;
//    }

    @Override
    public List<ArticleVendu> findArticles(int page, int size, String sortBy, String sortDir) {
        // Sécurité pour éviter les injections SQL dans sortBy / sortDir
        if (!sortBy.matches("^[a-zA-Z_]+$")) {
            sortBy = "date_fin_encheres";
        }
        if (!sortDir.equalsIgnoreCase("asc") && !sortDir.equalsIgnoreCase("desc")) {
            sortDir = "asc";
        }

        String sql = "SELECT * FROM article_vendu " +
                "ORDER BY " + sortBy + " " + sortDir +
                " LIMIT ? OFFSET ?";

        int offset = (page - 1) * size;

        return jdbcTemplate.query(sql, new ArticleVenduRowMapper(), size, offset);
    }

    // ⚠️ Les autres méthodes de ArticleVenduDAO sont à implémenter aussi
    @Override
    public void add(ArticleVendu a) {}

    @Override
    public void modifier(ArticleVendu a) {}

    @Override
    public void delete(ArticleVendu a) {}

    @Override
    public List<ArticleVendu> findByCat(String libelle) {
        return List.of();
    }

    @Override
    public String trouvePseudoParNo(int no) {
        return "";
    }

    @Override
    public String trouveCategorieParNo(int no) {
        return "";
    }

    private final static String FIND_ALL_CATEGORIES = "SELECT no_categorie, libelle FROM categorie";

//    @Override
//    public List<Categorie> findAllCategories() {
//        return jdbcTemplate.query(FIND_ALL_CATEGORIES, new BeanPropertyRowMapper<>(Categorie.class));
//    }

//    public class ArticleVenduRowMapper implements RowMapper<ArticleVendu> {

//        @Override
//        public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException {
//            ArticleVendu a = new ArticleVendu();
//            a.setNoArticle(rs.getInt("no_article"));
//            a.setNomArticle(rs.getString("nom_article"));
//            a.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
//            a.setMiseaPrix(rs.getInt("prix_initial"));
//            a.setPrixVente(rs.getInt("prix_vente"));
//            return a;
//        }
//    }
}
