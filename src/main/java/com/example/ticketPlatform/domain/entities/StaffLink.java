package com.example.ticketPlatform.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffLink {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name ="organizer_id")
    private User organizer;

    @ManyToOne
    @JoinColumn(name="staffer_id")
    private User staffer;

    @Column(name="score")
    private int score ;

    @Column(name ="note",nullable = true)
    private String note;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private LinkStatusEnum status;


}
