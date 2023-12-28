package net.warhasher.swapper.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "developer")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DeveloperEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "developer", fetch = FetchType.LAZY)
    private Set<InventoryEntity> inventory = new HashSet<>();
}
