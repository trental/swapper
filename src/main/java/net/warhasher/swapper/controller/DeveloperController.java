package net.warhasher.swapper.controller;

import lombok.AllArgsConstructor;
import net.warhasher.swapper.dto.DeveloperDto;
import net.warhasher.swapper.dto.InventoryDto;
import net.warhasher.swapper.service.DeveloperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/developers")
public class DeveloperController {

    private DeveloperService developerService;

    @PostMapping
    public ResponseEntity<DeveloperDto> createDeveloper(@RequestBody DeveloperDto developer){
        DeveloperDto savedDeveloper = developerService.createDeveloper(developer);

        return new ResponseEntity<>(savedDeveloper, HttpStatus.CREATED);
    }

    @PutMapping("/{developerId}/equipment/{equipmentId}")
    public ResponseEntity<DeveloperDto> updateDeveloperInventory(
            @PathVariable("developerId") UUID developerId,
            @PathVariable("equipmentId") UUID equipmentId,
            @RequestBody InventoryDto inventory) throws Exception {
        DeveloperDto savedDeveloper = developerService.updateDeveloperInventory(developerId, equipmentId, inventory.getQuantity());

        return new ResponseEntity<>(savedDeveloper, HttpStatus.CREATED);
    }
}
