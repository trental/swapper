package net.warhasher.swapper.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "swap")
public class Swap {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false)
    private UUID inId;

    @Column(nullable = false)
    private UUID outId;

    @Column(nullable = false)
    private UUID developerId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // New field for createdAt timestamp

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now(ZoneOffset.UTC); // Automatically set createdAt timestamp on creation
    }
}
