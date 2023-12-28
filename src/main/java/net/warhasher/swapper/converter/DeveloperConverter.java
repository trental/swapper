package net.warhasher.swapper.converter;

import jakarta.transaction.Transactional;
import net.warhasher.swapper.dto.DeveloperDto;
import net.warhasher.swapper.dto.InventoryDto;
import net.warhasher.swapper.entity.Developer;
import net.warhasher.swapper.entity.Inventory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DeveloperConverter {

    EquipmentConverter equipmentConverter;

    public DeveloperDto convertToDeveloperDto(Developer developer){
        DeveloperDto developerDto = new DeveloperDto();
        developerDto.setId(developer.getId());
        developerDto.setName(developer.getName());

        Set<InventoryDto> inventoryDtoSet = new HashSet<>();

        Set<Inventory> developerInventory = developer.getInventory();

        for(Inventory inventory : developerInventory) {
            InventoryDto inventoryDto = new InventoryDto();
            inventoryDto.setEquipment(equipmentConverter.convertToEquipmentDto(inventory.getEquipment()));
            inventoryDto.setQuantity(inventory.getQuantity());

            inventoryDtoSet.add(inventoryDto);
        }

        developerDto.setInventory(inventoryDtoSet);

        return developerDto;
    }

    public Developer convertToDeveloper(DeveloperDto developerDto){
        Set<Inventory> inventory = new HashSet<>();

        return new Developer(
                developerDto.getId(),
                developerDto.getName(),
                inventory
        );
    }
}
