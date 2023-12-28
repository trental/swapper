package net.warhasher.swapper.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory")
public class InventoryEntity {

    @EmbeddedId
    InventoryKey id;

    @ManyToOne
    @MapsId("developerId")
    @JoinColumn(name = "developer_id")
    private DeveloperEntity developer;

    @ManyToOne
    @MapsId("equipmentId")
    @JoinColumn(name = "equipment_id")
    private EquipmentEntity equipment;

    private Integer quantity;
}
