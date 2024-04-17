package com.gm2.crypto_project.repository;

import com.gm2.crypto_project.models.CoinModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CoinRepository extends JpaRepository<CoinModel, UUID> {
    List<CoinModel> findByActiveTbTrue();
    Optional<CoinModel> findByName(String name);
}
