package net.warhasher.swapper.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory")
public class Inventory {

    @EmbeddedId
    InventoryKey id;

    @ManyToOne
    @MapsId("developerId")
    @JoinColumn(name = "developer_id")
    private Developer developer;

    @ManyToOne
    @MapsId("equipmentId")
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    private Integer quantity;
}
