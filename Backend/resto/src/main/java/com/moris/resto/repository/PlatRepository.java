package com.moris.resto.repository;

import com.moris.resto.entity.Plat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlatRepository extends JpaRepository<Plat, Long> {

    boolean existsByCodePlat(String code);
}
