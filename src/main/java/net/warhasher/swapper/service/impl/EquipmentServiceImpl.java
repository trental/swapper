package net.warhasher.swapper.service.impl;

import lombok.AllArgsConstructor;
import net.warhasher.swapper.converter.EquipmentConverter;
import net.warhasher.swapper.dto.EquipmentDto;
import net.warhasher.swapper.entity.EquipmentEntity;
import net.warhasher.swapper.repository.EquipmentRepository;
import net.warhasher.swapper.service.EquipmentService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {

    private EquipmentRepository equipmentRepository;
    private EquipmentConverter equipmentConverter;

    @Override
    public EquipmentDto createEquipment(EquipmentDto equipmentDto) {
        EquipmentEntity equipment = equipmentConverter.convertToEquipment(equipmentDto);

        EquipmentEntity savedEquipment = equipmentRepository.save(equipment);

        EquipmentDto savedEquipmentDto = equipmentConverter.convertToEquipmentDto(savedEquipment);

        return savedEquipmentDto;
    }
}
