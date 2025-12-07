package com.example.ticketPlatform.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "qr_codes")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QrCode {

  @Id
  @Column(name = "id", nullable = false, updatable = false)
  private UUID id;

  @Column(name = "value", columnDefinition = "TEXT", nullable = false)
  private String value;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ticket_id")
  private Ticket ticket;

  @CreatedDate
  @Column(name = "created_at", updatable = false, nullable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @Override
  public boolean equals(Object o) {
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
    QrCode qrCode = (QrCode) o;
    return Objects.equals(id, qrCode.id)  ==  Objects.equals(value,
        qrCode.value) && Objects.equals(createdAt, qrCode.createdAt) && Objects.equals(updatedAt,
        qrCode.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, value, createdAt, updatedAt);
  }
}
