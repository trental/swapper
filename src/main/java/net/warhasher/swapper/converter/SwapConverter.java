package net.warhasher.swapper.converter;

import net.warhasher.swapper.dto.SwapDto;
import net.warhasher.swapper.entity.SwapEntity;
import org.springframework.stereotype.Component;

@Component
public class SwapConverter {

    public SwapDto convertToDto(SwapEntity swap) {
        SwapDto swapDto = new SwapDto();
        swapDto.setId(swap.getId());
        swapDto.setInId(swap.getInId());
        swapDto.setOutId(swap.getOutId());
        swapDto.setDeveloperId(swap.getDeveloperId());
        swapDto.setCreatedAt(swap.getCreatedAt());
        return swapDto;
    }

    public SwapEntity convertToEntity(SwapDto swapDto) {
        SwapEntity swap = new SwapEntity();
        swap.setId(swapDto.getId());
        swap.setInId(swapDto.getInId());
        swap.setOutId(swapDto.getOutId());
        swap.setDeveloperId(swapDto.getDeveloperId());
        swap.setCreatedAt(swapDto.getCreatedAt());
        return swap;
    }
}
