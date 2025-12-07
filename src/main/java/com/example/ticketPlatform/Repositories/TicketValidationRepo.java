package com.example.ticketPlatform.Repositories;

import com.example.ticketPlatform.domain.entities.TicketValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TicketValidationRepo extends JpaRepository<TicketValidation, UUID> {
}
