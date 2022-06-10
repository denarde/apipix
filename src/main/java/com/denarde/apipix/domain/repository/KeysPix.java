package com.denarde.apipix.domain.repository;

import com.denarde.apipix.domain.entity.KeyPix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeysPix extends JpaRepository<KeyPix, Integer> {

    Optional<KeyPix> getByKey(String key);

    boolean existsByKey(String key);
}
