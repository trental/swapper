package net.warhasher.swapper.controller;

import lombok.AllArgsConstructor;
import net.warhasher.swapper.dto.DeveloperDto;
import net.warhasher.swapper.dto.InventoryDto;
import net.warhasher.swapper.dto.SwapDto;
import net.warhasher.swapper.exception.ResourceNotFoundException;
import net.warhasher.swapper.service.DeveloperService;
import net.warhasher.swapper.service.SwapService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/developers")
public class DeveloperController {

    private DeveloperService developerService;
    private SwapService swapService;

    @PostMapping
    public ResponseEntity<DeveloperDto> createDeveloper(@RequestBody DeveloperDto developer){
        DeveloperDto savedDeveloper = developerService.createDeveloper(developer);

        return new ResponseEntity<>(savedDeveloper, HttpStatus.CREATED);
    }

    @PutMapping("/{developerId}")
    public ResponseEntity<DeveloperDto> updateDeveloper(@RequestBody DeveloperDto developer){
        DeveloperDto savedDeveloper = developerService.updateDeveloper(developer);

        return new ResponseEntity<>(savedDeveloper, HttpStatus.OK);
    }

    @GetMapping("/{developerId}")
    public ResponseEntity<DeveloperDto> getDeveloperById(@PathVariable("developerId") UUID developerId){
        DeveloperDto developerDto = developerService.retrieveDeveloper(developerId);

        return new ResponseEntity<>(developerDto, HttpStatus.OK);
    }

    @PutMapping("/{developerId}/equipment/{equipmentId}")
    public ResponseEntity<DeveloperDto> updateDeveloperInventory(
            @PathVariable("developerId") UUID developerId,
            @PathVariable("equipmentId") UUID equipmentId,
            @RequestBody InventoryDto inventory) throws Exception {
        DeveloperDto savedDeveloper = developerService.updateDeveloperInventory(developerId, equipmentId, inventory.getQuantity());

        return new ResponseEntity<>(savedDeveloper, HttpStatus.CREATED);
    }

    @PostMapping("/{developerId}/swaps")
    public ResponseEntity<SwapDto> createSwap(
            @PathVariable("developerId") UUID developerId,
            @RequestBody SwapDto swapDto) {
        swapDto.setDeveloperId(developerId);
        SwapDto createdSwap = swapService.createSwap(swapDto);
        return new ResponseEntity<>(createdSwap, HttpStatus.CREATED);
    }

    @DeleteMapping("/{developerId}/swaps/{swapId}")
    public ResponseEntity<Void> deleteSwap(@PathVariable("swapId") UUID swapId) {
        swapService.deleteSwap(swapId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
