package com.denarde.apipix.domain.repository;

import com.denarde.apipix.domain.entity.KeyPix;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeysPix extends JpaRepository<KeyPix, Integer> {

    public Optional<KeyPix> getByKey(String key);
}
