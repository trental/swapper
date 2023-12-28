package net.warhasher.swapper.converter;

import net.warhasher.swapper.dto.EquipmentDto;
import net.warhasher.swapper.entity.Equipment;
import net.warhasher.swapper.entity.Inventory;

import java.util.HashSet;
import java.util.Set;

public class EquipmentConverter {

    public static EquipmentDto convertToEquipmentDto(Equipment equipment){
        return new EquipmentDto(
                equipment.getId(),
                equipment.getType(),
                equipment.getName()
        );
    }

    public static Equipment convertToEquipment(EquipmentDto equipmentDto){
        Set<Inventory> inventory = new HashSet<>();

        return new Equipment(
                equipmentDto.getId(),
                equipmentDto.getType(),
                equipmentDto.getName(),
                inventory
        );
    }
}
