package net.warhasher.swapper.converter;

import net.warhasher.swapper.dto.InventoryDto;
import net.warhasher.swapper.entity.InventoryEntity;
import org.springframework.stereotype.Component;

@Component
public class InventoryConverter {

    EquipmentConverter equipmentConverter;

    public InventoryDto convertToInventoryDto(InventoryEntity inventory){
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setEquipment(equipmentConverter.convertToEquipmentDto(inventory.getEquipment()));
        inventoryDto.setQuantity(inventory.getQuantity());

        return inventoryDto;
    }
}
