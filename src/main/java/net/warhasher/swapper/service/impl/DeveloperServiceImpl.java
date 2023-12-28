package net.warhasher.swapper.service.impl;

import lombok.AllArgsConstructor;
import net.warhasher.swapper.converter.DeveloperConverter;
import net.warhasher.swapper.dto.DeveloperDto;
import net.warhasher.swapper.entity.Developer;
import net.warhasher.swapper.repository.DeveloperCustomRepository;
import net.warhasher.swapper.repository.DeveloperRepository;
import net.warhasher.swapper.repository.InventoryRepository;
import net.warhasher.swapper.service.DeveloperService;
import net.warhasher.swapper.service.InventoryService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DeveloperServiceImpl implements DeveloperService {

    private DeveloperRepository developerRepository;
    private DeveloperCustomRepository developerCustomRepository;
    private InventoryService inventoryService;

    @Override
    public DeveloperDto createDeveloper(DeveloperDto developerDto) {
        Developer developer = DeveloperConverter.convertToDeveloper(developerDto);

        Developer savedDeveloper = developerRepository.save(developer);

        DeveloperDto savedDeveloperDto = DeveloperConverter.convertToDeveloperDto(savedDeveloper);

        return savedDeveloperDto;
    }

    @Override
    public DeveloperDto updateDeveloperInventory(UUID developerId, UUID equipmentId, Integer quantity) throws Exception {

        inventoryService.setInventory(developerId, equipmentId, quantity);

//        Optional<Developer> developer = developerCustomRepository.getDeveloperWithInventory(developerId);
        Optional<Developer> developer = developerRepository.findById(developerId);

        if (developer.isEmpty()) {
            throw new Exception("not found");
        }

        return DeveloperConverter.convertToDeveloperDto(developer.get());
    }
}
