package net.warhasher.swapper.service.impl;

import lombok.AllArgsConstructor;
import net.warhasher.swapper.converter.DeveloperConverter;
import net.warhasher.swapper.dto.DeveloperDto;
import net.warhasher.swapper.entity.DeveloperEntity;
import net.warhasher.swapper.repository.DeveloperRepository;
import net.warhasher.swapper.service.DeveloperService;
import net.warhasher.swapper.service.InventoryService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DeveloperServiceImpl implements DeveloperService {

    private DeveloperRepository developerRepository;
    private InventoryService inventoryService;
    private DeveloperConverter developerConverter;

    @Override
    public DeveloperDto createDeveloper(DeveloperDto developerDto) {
        DeveloperEntity developer = developerConverter.convertToDeveloper(developerDto);

        DeveloperEntity savedDeveloper = developerRepository.save(developer);

        DeveloperDto savedDeveloperDto = developerConverter.convertToDeveloperDto(savedDeveloper);

        return savedDeveloperDto;
    }

    @Override
    public DeveloperDto updateDeveloper(DeveloperDto developerDto) {
        Optional<DeveloperEntity> existingDeveloper = developerRepository.findById(developerDto.getId());

        if (existingDeveloper.isPresent()) {
            DeveloperEntity savedDeveloper = developerRepository.save(developerConverter.convertToDeveloper(developerDto));

            return developerConverter.convertToDeveloperDto(savedDeveloper);
        } else {
            throw new RuntimeException("not found");
        }
    }

    @Override
    public DeveloperDto retrieveDeveloper(UUID developerId) {
        Optional<DeveloperEntity> developer = developerRepository.findById(developerId);

        if (developer.isPresent()) {
            return developerConverter.convertToDeveloperDto(developer.get());
        } else {
            throw new RuntimeException("not found");
        }
    }

    @Override
    public DeveloperDto updateDeveloperInventory(UUID developerId, UUID equipmentId, Integer quantity) throws Exception {

        inventoryService.setInventory(developerId, equipmentId, quantity);

        Optional<DeveloperEntity> developer = developerRepository.findById(developerId);

        if (developer.isEmpty()) {
            throw new Exception("not found");
        }

        return developerConverter.convertToDeveloperDto(developer.get());
    }
}
