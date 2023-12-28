package net.warhasher.swapper.converter;

import net.warhasher.swapper.dto.EquipmentDto;
import net.warhasher.swapper.entity.EquipmentEntity;
import net.warhasher.swapper.entity.InventoryEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class EquipmentConverter {

    public EquipmentDto convertToEquipmentDto(EquipmentEntity equipment){
        return new EquipmentDto(
                equipment.getId(),
                equipment.getType(),
                equipment.getName()
        );
    }

    public EquipmentEntity convertToEquipment(EquipmentDto equipmentDto){
        Set<InventoryEntity> inventory = new HashSet<>();

        return new EquipmentEntity(
                equipmentDto.getId(),
                equipmentDto.getType(),
                equipmentDto.getName(),
                inventory
        );
    }
}
