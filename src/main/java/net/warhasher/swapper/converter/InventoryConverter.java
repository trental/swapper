package net.warhasher.swapper.converter;

import net.warhasher.swapper.dto.InventoryDto;
import net.warhasher.swapper.entity.Inventory;
import org.springframework.beans.factory.annotation.Autowired;

import static net.warhasher.swapper.converter.DeveloperConverter.convertToDeveloperDto;
import static net.warhasher.swapper.converter.EquipmentConverter.convertToEquipmentDto;

public class InventoryConverter {

    public static InventoryDto convertToInventoryDto(Inventory inventory){
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setEquipment(convertToEquipmentDto(inventory.getEquipment()));
        inventoryDto.setQuantity(inventory.getQuantity());

        return inventoryDto;
    }
}
