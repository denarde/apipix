package com.denarde.apipix.domain.repository;

import com.denarde.apipix.domain.entity.ReceivedPix;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceivedsPix extends JpaRepository<ReceivedPix, Integer> {
}
