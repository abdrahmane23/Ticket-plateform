package com.example.ticketPlatform.Repositories;

import com.example.ticketPlatform.domain.entities.LinkStatusEnum;
import com.example.ticketPlatform.domain.entities.StaffLink;
import com.example.ticketPlatform.domain.entities.TaskStatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface StaffLinkRepo extends JpaRepository<StaffLink, UUID> {
    boolean existsByStafferIdAndStatus(UUID stafferId, LinkStatusEnum status);
    boolean existsByStafferIdAndOrganizerIdAndStatus(UUID receiverId, UUID senderId, LinkStatusEnum status);
    Optional <StaffLink> findByIdAndStatus(UUID Id, LinkStatusEnum status);

}
