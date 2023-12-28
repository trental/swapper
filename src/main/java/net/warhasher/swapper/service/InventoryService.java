package net.warhasher.swapper.service;

import java.util.UUID;

public interface InventoryService {

    void setInventory(UUID developer, UUID equipment, Integer quantity);
}
