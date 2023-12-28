package net.warhasher.swapper.converter;

import net.warhasher.swapper.dto.DeveloperDto;
import net.warhasher.swapper.dto.InventoryDto;
import net.warhasher.swapper.entity.DeveloperEntity;
import net.warhasher.swapper.entity.InventoryEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DeveloperConverter {

    EquipmentConverter equipmentConverter;

    public DeveloperDto convertToDeveloperDto(DeveloperEntity developer){
        DeveloperDto developerDto = new DeveloperDto();
        developerDto.setId(developer.getId());
        developerDto.setName(developer.getName());

        Set<InventoryDto> inventoryDtoSet = new HashSet<>();

        Set<InventoryEntity> developerInventory = developer.getInventory();

        for(InventoryEntity inventory : developerInventory) {
            InventoryDto inventoryDto = new InventoryDto();
            inventoryDto.setEquipment(equipmentConverter.convertToEquipmentDto(inventory.getEquipment()));
            inventoryDto.setQuantity(inventory.getQuantity());

            inventoryDtoSet.add(inventoryDto);
        }

        developerDto.setInventory(inventoryDtoSet);

        return developerDto;
    }

    public DeveloperEntity convertToDeveloper(DeveloperDto developerDto){
        Set<InventoryEntity> inventory = new HashSet<>();

        return new DeveloperEntity(
                developerDto.getId(),
                developerDto.getName(),
                inventory
        );
    }
}
