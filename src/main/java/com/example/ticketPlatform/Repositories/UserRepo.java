package com.example.ticketPlatform.Repositories;

import com.example.ticketPlatform.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {
    Page<User> findByRole(String role, Pageable pageable);

    Optional<User> findByIdAndRole(UUID organizerId, String organizerRole);
}
