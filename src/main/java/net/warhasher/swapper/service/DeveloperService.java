package net.warhasher.swapper.service;

import net.warhasher.swapper.dto.DeveloperDto;

import java.util.UUID;

public interface DeveloperService {

    DeveloperDto createDeveloper(DeveloperDto developer);

    DeveloperDto updateDeveloperInventory(UUID developer, UUID equipment, Integer quantity) throws Exception;

}
