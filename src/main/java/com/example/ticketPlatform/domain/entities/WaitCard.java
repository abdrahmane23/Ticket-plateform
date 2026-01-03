package com.example.ticketPlatform.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "wait_list",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"ticketType_id", "user_id"}
                )
        } )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WaitCard {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User waiter;

    @ManyToOne
    @JoinColumn(name = "ticketType_id", nullable = false)
    private TicketType ticketType;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
