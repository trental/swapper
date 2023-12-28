package net.warhasher.swapper.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class InventoryKey implements Serializable {

    @Column(name = "developer_id")
    UUID developerId;

    @Column(name = "equipment_id")
    UUID equipmentId;
}
