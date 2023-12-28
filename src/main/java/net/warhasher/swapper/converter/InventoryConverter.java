package net.warhasher.swapper.converter;

import net.warhasher.swapper.dto.InventoryDto;
import net.warhasher.swapper.entity.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryConverter {

    EquipmentConverter equipmentConverter;

    public InventoryDto convertToInventoryDto(Inventory inventory){
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setEquipment(equipmentConverter.convertToEquipmentDto(inventory.getEquipment()));
        inventoryDto.setQuantity(inventory.getQuantity());

        return inventoryDto;
    }
}
