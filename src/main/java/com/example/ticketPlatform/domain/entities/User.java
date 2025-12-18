package com.example.ticketPlatform.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "email", nullable = false)
  private String email;


  @OneToMany(mappedBy = "purchaser", cascade = CascadeType.ALL)
  private List<Ticket> tickets = new ArrayList<>();

  @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL)
  private List<Event> organizedEvents = new ArrayList<>();

  @OneToMany(mappedBy = "sender")
  private List<Request> sentRequests= new ArrayList<>();

  @OneToMany(mappedBy = "receiver")
  private List<Request> receivedRequests= new ArrayList<>();

  @OneToOne(mappedBy = "staffer", cascade = CascadeType.ALL)
  private StaffLink staffLink;
  @OneToMany(mappedBy = "staffer", cascade = CascadeType.ALL)
  private List<Task> tasks= new ArrayList<>();

  @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL)
  private List<StaffLink> StaffMembers = new ArrayList<>();
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
    User user = (User) o;
    return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email,
        user.email) && Objects.equals(createdAt, user.createdAt) && Objects.equals(updatedAt,
        user.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, email, createdAt, updatedAt);
  }
}
