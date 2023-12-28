package net.warhasher.swapper.converter;

import jakarta.transaction.Transactional;
import net.warhasher.swapper.dto.DeveloperDto;
import net.warhasher.swapper.dto.InventoryDto;
import net.warhasher.swapper.entity.Developer;
import net.warhasher.swapper.entity.Inventory;

import java.util.HashSet;
import java.util.Set;

import static net.warhasher.swapper.converter.EquipmentConverter.convertToEquipmentDto;

public class DeveloperConverter {

    public static DeveloperDto convertToDeveloperDto(Developer developer){
        DeveloperDto developerDto = new DeveloperDto();
        developerDto.setId(developer.getId());
        developerDto.setName(developer.getName());

        Set<InventoryDto> inventoryDtoSet = new HashSet<>();

        Set<Inventory> developerInventory = developer.getInventory();

        for(Inventory inventory : developerInventory) {
            InventoryDto inventoryDto = new InventoryDto();
            inventoryDto.setEquipment(convertToEquipmentDto(inventory.getEquipment()));
            inventoryDto.setQuantity(inventory.getQuantity());

            inventoryDtoSet.add(inventoryDto);
        }

        developerDto.setInventory(inventoryDtoSet);

        return developerDto;
    }

    public static Developer convertToDeveloper(DeveloperDto developerDto){
        Set<Inventory> inventory = new HashSet<>();

        return new Developer(
                developerDto.getId(),
                developerDto.getName(),
                inventory
        );
    }
}
