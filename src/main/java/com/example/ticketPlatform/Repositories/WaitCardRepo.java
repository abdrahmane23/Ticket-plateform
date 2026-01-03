package com.example.ticketPlatform.Repositories;


import com.example.ticketPlatform.domain.entities.WaitCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface WaitCardRepo extends JpaRepository<WaitCard, UUID> {

    Optional<WaitCard> findFirstByTicketTypeIdOrderByCreatedAtAsc(UUID ticketTypeId);

    void deleteByTicketTypeIdAndWaiterId(UUID ticketTypeId, UUID id);
}
