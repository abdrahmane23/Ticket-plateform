package com.example.ticketPlatform.Repositories;

import aj.org.objectweb.asm.commons.Remapper;
import com.example.ticketPlatform.domain.entities.LinkStatusEnum;
import com.example.ticketPlatform.domain.entities.StaffLink;
import com.example.ticketPlatform.domain.entities.TaskStatusEnum;
import com.example.ticketPlatform.domain.entities.User;
import com.google.zxing.client.result.CalendarParsedResult;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface StaffLinkRepo extends JpaRepository<StaffLink, UUID> {
    StaffLink findByStatusAndStafferId(LinkStatusEnum linkStatusEnum,UUID stafferId);

    boolean existsByStafferIdAndStatus(UUID stafferId, LinkStatusEnum status);
    boolean existsByStafferIdAndOrganizerIdAndStatus(UUID receiverId, UUID senderId, LinkStatusEnum status);
    Optional <StaffLink> findByIdAndStatus(UUID Id, LinkStatusEnum status);
    Page<StaffLink> findAllByOrganizerIdAndStatus(UUID organizerId, LinkStatusEnum linkStatusEnum, Pageable pageable);

    StaffLink findByOrganizerIdAndStatusAndStafferId(UUID organizerId,LinkStatusEnum linkStatusEnum, UUID stafferId);
}
