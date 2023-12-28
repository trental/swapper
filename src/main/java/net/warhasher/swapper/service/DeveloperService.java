package net.warhasher.swapper.service;

import net.warhasher.swapper.dto.DeveloperDto;

import java.util.UUID;

public interface DeveloperService {

    DeveloperDto createDeveloper(DeveloperDto developer);

    DeveloperDto updateDeveloper(DeveloperDto developerDto);

    DeveloperDto retrieveDeveloper(UUID developerId);

    DeveloperDto updateDeveloperInventory(UUID developer, UUID equipment, Integer quantity) throws Exception;

}
