
package fr.eni.projetencheres.dal;

import fr.eni.projetencheres.bo.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByPseudo(String pseudo);
}
