package com.example.ticketPlatform.Repositories;

import com.example.ticketPlatform.domain.entities.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QrCodeRepo extends JpaRepository<QrCode, UUID> {

    Optional<QrCode> findByTicketIdAndTicketPurchaserId(UUID ticketId, UUID userId);
    Optional<QrCode> findByValue(String Value);
}
