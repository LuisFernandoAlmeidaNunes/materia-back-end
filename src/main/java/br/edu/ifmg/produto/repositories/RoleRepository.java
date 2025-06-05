package br.edu.ifmg.produto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.ifmg.produto.entities.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    public Role findByAuthority(String authority);

}
