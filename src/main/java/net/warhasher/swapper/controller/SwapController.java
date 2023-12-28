package net.warhasher.swapper.controller;

import lombok.AllArgsConstructor;
import net.warhasher.swapper.dto.SwapDto;
import net.warhasher.swapper.exception.ResourceNotFoundException;
import net.warhasher.swapper.service.SwapService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/swaps")
public class SwapController {

    private SwapService swapService;

    @PostMapping
    public ResponseEntity<SwapDto> createSwap(@RequestBody SwapDto swapDto) {
        SwapDto createdSwap = swapService.createSwap(swapDto);
        return new ResponseEntity<>(createdSwap, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSwap(@PathVariable UUID id) {
        swapService.deleteSwap(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
