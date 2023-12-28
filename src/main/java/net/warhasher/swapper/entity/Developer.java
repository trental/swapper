package net.warhasher.swapper.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Developer {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "developer", fetch = FetchType.LAZY)
    private Set<Inventory> inventory = new HashSet<>();
}
