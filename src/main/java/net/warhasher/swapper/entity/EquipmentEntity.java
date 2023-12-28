package net.warhasher.swapper.entity;

import jakarta.persistence.*;
import lombok.*;
import net.warhasher.swapper.data.EquipmentType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "equipment")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EquipmentEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Enumerated(EnumType.STRING)
    private EquipmentType type;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "equipment")
    private Set<InventoryEntity> inventory = new HashSet<>();
}
