package net.warhasher.swapper.service.impl;

import lombok.AllArgsConstructor;
import net.warhasher.swapper.dto.InventoryDto;
import net.warhasher.swapper.repository.InventoryRepository;
import net.warhasher.swapper.service.InventoryService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    InventoryRepository inventoryRepository;
    @Override
    public void setInventory(UUID developer, UUID equipment, Integer quantity) {
        inventoryRepository.setInventory(developer, equipment, quantity);
    }
}
