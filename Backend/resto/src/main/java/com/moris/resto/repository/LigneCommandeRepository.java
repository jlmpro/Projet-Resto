package com.moris.resto.repository;

import com.moris.resto.entity.LigneCommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneCommandeRepository extends JpaRepository<LigneCommandeClient, Long> {
}
