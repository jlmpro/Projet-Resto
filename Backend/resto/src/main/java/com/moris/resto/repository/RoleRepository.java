package com.moris.resto.repository;

import com.moris.resto.entity.Role;
import com.moris.resto.enums.TypeDeRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByLibelle(TypeDeRole libelle);

    boolean existsByLibelle(TypeDeRole typeDeRole);
}
