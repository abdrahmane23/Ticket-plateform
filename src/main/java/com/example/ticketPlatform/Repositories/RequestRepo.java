package com.example.ticketPlatform.Repositories;

import com.example.ticketPlatform.domain.entities.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RequestRepo extends JpaRepository<Request, UUID> {
    Page<Request> findByReceiverId(UUID userId, Pageable pageable);
}
