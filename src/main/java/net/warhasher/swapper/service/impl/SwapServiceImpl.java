package net.warhasher.swapper.service.impl;

import lombok.AllArgsConstructor;
import net.warhasher.swapper.converter.SwapConverter;
import net.warhasher.swapper.dto.SwapDto;
import net.warhasher.swapper.entity.Swap;
import net.warhasher.swapper.exception.ResourceNotFoundException;
import net.warhasher.swapper.repository.SwapRepository;
import net.warhasher.swapper.service.SwapService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class SwapServiceImpl implements SwapService {

    private final SwapRepository swapRepository;
    private final SwapConverter swapConverter;

    @Override
    public SwapDto createSwap(SwapDto swapDto) {
        Swap swap = swapConverter.convertToEntity(swapDto);
        swap.setId(UUID.randomUUID()); // Generating UUID for the swap

        Swap savedSwap = swapRepository.save(swap);
        return swapConverter.convertToDto(savedSwap);
    }

    @Override
    public void deleteSwap(UUID id) {
        Swap existingSwap = swapRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Swap not found with ID: " + id));
        swapRepository.delete(existingSwap);
    }
}
