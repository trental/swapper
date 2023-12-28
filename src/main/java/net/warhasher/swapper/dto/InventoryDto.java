package net.warhasher.swapper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.warhasher.swapper.entity.Developer;
import net.warhasher.swapper.entity.Equipment;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDto {

    private EquipmentDto equipment;

    private Integer quantity;
}
